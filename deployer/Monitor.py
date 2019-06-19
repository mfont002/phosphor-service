import time
from watchdog.observers import Observer
from watchdog.events import PatternMatchingEventHandler
from configobj import ConfigObj
import ProcessJob as Runner
from __init__ import get_logger

logger = get_logger(__name__)


class Monitor(PatternMatchingEventHandler, Observer):
    def __init__(self, args='./in-files/'):
        event = PatternMatchingEventHandler.__init__(self, '*', '', True, False)
        obs = Observer()
        self.obs = obs
        self.path = args
        self.event = event
        self.obs.schedule(self, self.path)
        self.start_monitor()

    def start_monitor(self):
        print('Starting')
        self.obs.start()
        try:
            print('Deployer is running')
            while True:
                time.sleep(1)
        except KeyboardInterrupt:
            self.obs.stop()

        self.obs.join()

    def on_modified(self, event):
        if event.event_type == 'modified':
            Monitor.process_file(event)

    def on_created(self, event):
        if event.event_type == 'created':
            Monitor.process_file(event)

    @staticmethod
    def process_file(event):
        if event.event_type == 'created' or event.event_type == 'modified':
            config = ConfigObj(event.src_path, list_values=True)
            client = config['default']['client']
            method = config['default']['method']

            config.get('default').pop('client')
            config.get('default').pop('method')
            Runner.ProcessJob(client, method, method, **config)


def start_monitor():
    Monitor()


if __name__ == '__main__':
    start_monitor()


