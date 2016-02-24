'''
Created on 23/gen/2016

@author: Enrico Benini, Nicola Casadei, Marco Benedetti
'''

from domain.streamworkers.AStreamWorker import AStreamWorker

class DummyConverter(AStreamWorker):
    '''
    classdocs
    '''


    def __init__(self, name):
        '''
        Constructor
        '''
        AStreamWorker.__init__(self, name)
        
    def on_next(self,x):
        self.outputStream.on_next(x)
        
    def on_error(self, error):
        self.outputStream.on_error(error)
        
    def on_completed(self):
        self.outputStream.on_completed()      