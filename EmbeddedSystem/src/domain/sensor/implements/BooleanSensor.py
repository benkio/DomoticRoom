'''
Created on 23/gen/2016

@author: nicola
'''
from domain.sensor.generic.ASensor import ASensor
from RPi import GPIO
from domain.streamworkers.messages.StreamMessage import StreamMessage
from datetime import datetime
#from time import sleep

class BooleanSensor(ASensor):
    '''
    classdocs
    '''


    def __init__(self, name, typeSensor,pin):
        '''
        Constructor
        '''
        ASensor.__init__(self, name, typeSensor, pin)
        self.controlFlag = True
        
    def run(self):
        ASensor.run(self)
        GPIO.setup(self.pin,GPIO.IN)
        GPIO.add_event_detect(self.pin,GPIO.BOTH,callback=self._callback_function)
        
    def _callback_function(self,channel):
        self.getValue() 
        
    def getValue(self):
        ASensor.getValue(self)
        if self.flag.is_set()== False:
            if (GPIO.input(self.pin)):
                s=StreamMessage(self.getName(),self.getType(),1,datetime.now())
                self.stream.on_next(s)
            else :
                s=StreamMessage(self.getName(),self.getType(),0,datetime.now())
                self.stream.on_next(s)
        else:
            self.stream.on_completed()
            
    def stop(self):
        ASensor.stop(self)
        GPIO.remove_event_detect(self.pin)