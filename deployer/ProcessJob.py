from boto3 import Session
import threading
from botocore import exceptions
import datetime
from __init__ import get_logger
from pathlib import Path
import os
import time
from FileUtils import FileUtils

logger = get_logger(__name__)


class ProcessJob (threading.Thread):
    def __init__(self, client, thread_id, filename, **args):
        threading.Thread.__init__(self)

        self.client = client
        self.id = thread_id
        self.name = thread_id
        self.filename = filename
        self.args = args
        self.status = 'processing'
        self.resp = ''
        self.vir_dir = ''
        self.zip_file = ''
        self.start()

    def create_session_type(self, obj_type):
        """
        boto3 service to use
        :param obj_type:
        :return:
        """
        session = Session()
        return session.client(obj_type, session.region_name)

    def param_converter(self, **args):
        """
        Converts string value to number or boolean based on type
        :param args:
        :return:
        """
        for key, val in args['default'].items():
            if isinstance(val, type('str')) or isinstance(val, type('int')):
                try:
                    num = int(val)
                    args['default'][key] = num
                except ValueError:
                    'non int'
                if val.lower() == 'true':
                    val = True
                    args['default'][key] = val
                elif val.lower() == 'false':
                    val = False
                    args['default'][key] = val
                else:
                    'none'
        return args

    def update_status(self):
        now = datetime.datetime.now()
        if self.status == 'error':
            with open(os.path.join('./out-error-files', self.name+'-error.txt'), 'w+') as out:
                out.write('\n'+now.strftime("%Y-%m-%d %H:%M:%S")+' {} ERROR.'.format(self.id))
                out.write('\n'+now.strftime("%Y-%m-%d %H:%M:%S")+' {}'.format(self.resp))
        elif self.status == 'finished':
            with open(os.path.join('./out-processed-files', self.name+'-processed.txt'), 'w+') as out:
                out.write('\n' + now.strftime("%Y-%m-%d %H:%M:%S") + ' {} Complete.'.format(self.id))
                out.write('\n' + now.strftime("%Y-%m-%d %H:%M:%S") + ' {}'.format(self.resp))

    def convert_to_bytes(self, **kwargs):
        """
        converts zip file contents to bytes when uploading to bucket.
        :param kwargs:
        :return:
        """
        bf = open(self.zip_file, 'r+b')
        pos = self.zip_file.rfind('/')
        kwargs.pop('zip')
        kwargs.pop('vir_dir')

        zip_name = self.zip_file[pos + 1:]

        kwargs['default']['Body'] = bf
        kwargs['default']['Key'] = zip_name

        return kwargs

    def create_zip(self, **kwargs):
        files = kwargs['zip']['files']
        zipfile = kwargs['zip']['zipfile']
        root_dir = kwargs['zip']['root_dir']
        self.zip_file = FileUtils.add_to_zip(zipfile, root_dir, *files)
        return self.zip_file

    @staticmethod
    def create_vir(**kwargs):
        vir_dir_name = kwargs['vir_dir']['vir_dir_name']
        FileUtils.create_vir_libs(vir_dir_name)

    def process_ini_file(self, obj, **kwargs):
        self.resp = getattr(obj, self.name)(**kwargs['default'])
        return self.resp

    @staticmethod
    def create_ssm(key, val):
        m_hold = dict()
        pm = dict()
        pm['Name'] = key
        pm['Value'] = val
        pm['Type'] = 'SecureString'
        pm['Overwrite'] = True

        m_hold.update(pm)
        return m_hold

    def run(self):
        try:
            curr_dir = os.path.dirname(Path(__file__).resolve())
            obj = self.create_session_type(self.client)
            kwargs = self.param_converter(**self.args)

            if 'vir_dir' in kwargs:
                self.create_vir(**kwargs)

            if 'zip' in kwargs:
                threader = threading.Thread(target=self.create_zip, kwargs={**kwargs})
                threader.start()
                while not self.zip_file:
                    time.sleep(2)
                    logger.debug('creating zipfile')
                logger.debug('zip file created: {} '.format(self.zip_file))

            if self.zip_file:
                args_t = self.convert_to_bytes(**kwargs)
                threader = threading.Thread(target=self.process_ini_file, args=(obj,), kwargs={**args_t})
                threader.start()
                while not self.resp:
                    time.sleep(5)
                    logger.debug('uploading zipfile to bucket')
                logger.debug('zipfile uploaded successfully')

            elif 'app' in kwargs:
                for key, val in kwargs['app'].items():
                    p_args = self.create_ssm(key, val)
                    self.resp = getattr(obj, self.name)(**p_args)
            else:
                threader = threading.Thread(target=self.process_ini_file, args=(obj,), kwargs={**kwargs})
                threader.start()
                while not self.resp:
                    time.sleep(3)
                    logger.debug('creating cloud resource from ini file')
                logger.debug('resource created successfully')

            os.chdir(curr_dir)

            self.status = 'finished'
            self.update_status()

        except (exceptions.ClientError, exceptions.ParamValidationError, Exception) as err:
            self.status = 'error'
            self.resp = str(err)
            self.update_status()
            raise
