'''
Created on 28/gen/2016

@author: nicola
'''
from domain.streamworkers.AStreamWorker import AStreamWorker
from domain.streamworkers.messages.StreamMessage import StreamMessage
from domain.sensorType.SensorType import SensorType

class TempConverter(AStreamWorker):
    '''
    classdocs
    '''


    def __init__(self, name):
        '''
        Constructor
        '''
        AStreamWorker.__init__(self, name)
        
    def on_next(self,x):
        if x.getTypeSensor() == SensorType.Temperature_DHT11 :
            dataReceived = x.getValore()
            tempValue=StreamMessage(x.getName()+"_Temperature",SensorType.Temperature_DHT11,dataReceived[2],x.getRelevationTime())
            humidityValue=StreamMessage(x.getName()+"_Humidity",SensorType.Humidity,dataReceived[0], x.getRelevationTime())
            self.outputStream.on_next(tempValue)
            #print(str(tempValue.getValore()))
            self.outputStream.on_next(humidityValue)
            #print(str(humidityValue.getValore()))
        
    def on_error(self, error):
        self.outputStream.on_error(error)
        
    def on_completed(self):
        self.outputStream.on_completed()      