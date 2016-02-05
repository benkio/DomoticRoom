'''
Created on 23/gen/2016

@author: nicola
'''
import time
from domain.sensor.generic.ASensor import ASensor
from RPi import GPIO
from domain.streamworkers.messages.StreamMessage import StreamMessage
from datetime import datetime


class TemperatureSensor(ASensor):
    '''
    classdocs
    '''
    def __init__(self, name, typeSensor,pin,delay):
        '''
        Constructor
        '''
        ASensor.__init__(self, name, typeSensor, pin)
        self.delay=delay
        
    def run(self):          
        while self.flag.is_set() == False :
            self.getValue()
            time.sleep(self.delay)
        self.stream.on_completed()
    
    def getValue(self):
        ASensor.getValue(self)
        try:
            GPIO.setup(self.pin, GPIO.OUT)
            #il pin va alto e si aspetta 
            self.__sendAndWait(GPIO.HIGH, 0.05)
            #il pin va basso e si aspetta
            self.__sendAndWait(GPIO.LOW, 0.02)
            #change GPIO setup mode
            GPIO.setup(self.pin,GPIO.IN, GPIO.PUD_UP)
            #collezioniamo i dati inviati in un array
            data =self.__saveData()
            newValue=StreamMessage(self.name,self.typeSensor,data,datetime.now())
            self.stream.on_next(newValue)
        except :
            self.stream.on_error("errore")
        
    def __sendAndWait(self, output, delay):
        GPIO.output(self.pin,output)
        time.sleep(delay)
        
    def __saveData(self):
        unchanged_count = 0
        
        max_unchanged_count=100
        
        last = -1
        data = []
        while True:
            current = GPIO.input(self.pin)
            data.append(current)
            if last != current:
                unchanged_count=0
                last = current
            else:
                unchanged_count +=1
                if unchanged_count > max_unchanged_count:
                    break
        return data