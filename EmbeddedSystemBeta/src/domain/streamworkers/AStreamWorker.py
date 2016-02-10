'''
Created on 24/gen/2016

@author: Enrico Benini, Nicola Casadei, Marco Benedetti
'''
from rx.observer import Observer
from rx.subjects.subject import Subject

class AStreamWorker(Observer):
    '''
    classdocs
    '''


    def __init__(self,name):
        '''
        Constructor
        '''
        Observer.__init__(self)
        self.name=name
        self.outputStream = Subject()
        
    def subscribeToInputStream(self,source):
        source.getOutputStream().subscribe(self)
        
    def getOutputStream(self):
        return self.outputStream