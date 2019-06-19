from pathlib import Path
import os
from os import error
import subprocess
from subprocess import CalledProcessError
import sys
from zipfile import ZipFile
import shutil
from __init__ import get_logger

logger = get_logger(__name__)


class FileUtils:
    def __init__(self):
        self.result = ''

    @staticmethod
    def create_vir_libs(venv_name, requirements='requirements.txt'):
        """
        creates virtual libs dir from requirements file

        :param venv_name: Name of virtual dir to create
        :param requirements: Text file containing project lib dependencies
        :return: virtual dir
        """
        try:
            main_dir = os.path.dirname(Path(Path(__file__).resolve().parent))
            os.chdir(main_dir)
            subprocess.check_call([sys.executable, '-m', 'venv', venv_name])

            v_dir = os.path.join(main_dir, venv_name)
            py_version = 'python' + str(sys.version_info[0]) + '.' + str(sys.version_info[1])
            v_dir_packages = os.path.join(v_dir, 'lib', py_version, 'site-packages')
            subprocess.check_call([sys.executable, '-m', 'pip', 'install', '-t', v_dir_packages, '-r', requirements])

            logger.info('lib dir created: {} '.format(venv_name))

        except (CalledProcessError, AttributeError, TypeError, error) as err:
            logger.error("Unable to install libs: {}".format(str(err)))
            shutil.rmtree(v_dir)
            raise

        return venv_name

    @staticmethod
    def add_to_zip(zip_file, root_dir_name, *argv):
        """
        recursively descends from root_dir_name and zips all files and or directory names passed in argv.
        root_dir_name must have write permission.

        :param zip_file: Name of zip file to create
        :param argv: directory and or file names to zip, separated by comma. If a dir name is given, all files under
        that dir will be zipped.  Prefacing the dir name with '../' will also include the dir name in the zip along
        with the files in that dir.
        :param rootdir_name: Name of top level dir from which to start searching for files and dir's to be zipped
        :return: zipped file
        """
        pos = root_dir_name.rfind('/')
        roots_dir_name = root_dir_name[pos + 1:]

        pos = os.getcwd().rfind('/')
        curr_chk = os.getcwd()[pos + 1:]
        base = os.path.dirname(Path(__file__).resolve())

        if curr_chk.lower() == roots_dir_name.lower():
            ''
        else:
            curr = os.path.dirname(Path(__file__).resolve())
            target_dir = os.path.dirname(Path(curr).resolve())

            while curr.lower() != roots_dir_name.lower() and target_dir != '/':
                next_dir = os.path.dirname(Path(target_dir).resolve())
                os.chdir(next_dir)
                to_name = next_dir
                pos = to_name.rfind('/')
                curr = to_name[pos + 1:]
                target_dir = next_dir
        main_dir = os.getcwd()

        top = 'n'
        for arg in argv:
            if '../' in arg:
                arg = arg.replace("../", "")
                top = 'y'
            for root, directories, files in os.walk('.'):
                for direct in directories:
                    if direct == arg:
                        os.chdir(os.path.join(root, direct))
                        break
            if '.' not in arg:
                with ZipFile(main_dir + '/' + zip_file, 'a') as zipper:
                    for root1, dirs1, files1 in os.walk('.'):
                        for filer in files1:
                            back = root1
                            if top == 'y':
                                root1 = os.path.join('../', arg, root1)
                            if '.pyc' not in filer and '.pyo' not in filer and 'test' not in filer \
                                    and 'test' not in root1:
                                path_filer = os.path.join(root1, filer)
                                zipper.write(path_filer)
                            root1 = back
                    top = 'n'
                    os.chdir(main_dir)

            with ZipFile(main_dir + '/'+zip_file, 'a') as zipper:
                for root, dirs, files in os.walk('.'):
                    for file in files:
                        if file == arg:
                            path_file = os.path.join(root, file)
                            zipper.write(path_file)
                            break
        zipper.close()
        return zip_file
