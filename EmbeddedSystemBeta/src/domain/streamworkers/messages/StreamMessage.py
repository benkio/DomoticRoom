'''
Created on 23/gen/2016

@author: Enrico Benini, Nicola Casadei, Marco Benedetti
'''
from datetime import datetime

class StreamMessage(object):
    '''
    classdocs
    '''
    

    def __init__(self, name, typeSensor, val, time=datetime.now()):
        '''
        Constructor
        '''
        self.valore=val
        self.typeSensor=typeSensor
        self.nameSensor=name
        self.relevationTime=time
        
    def getValore(self):
        return self.valore
    
    def setValore(self, val):
        self.valore=val
        
    def getName(self):
        return self.nameSensor
    
    def getTypeSensor(self):
        return self.typeSensor
    
    def getRelevationTime(self):
        return self.relevationTime