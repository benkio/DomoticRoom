'''
Created on 29/gen/2016

@author: nicola
'''
import socket
from RPi import GPIO
from domain.sensorType.SensorType import SensorType
from domain.sensor.implements.TemperatureSensor import TemperatureSensor
from domain.sensor.implements.BooleanSensor import BooleanSensor
from domain.streamworkers.packagers.Packager import Packager
from domain.streamworkers.Sender import Sender
from domain.streamworkers.validators.TempSensorValidator import TempSensorValidator
from domain.streamworkers.converters.TempConverter import TempConverter
from domain.streamworkers.converters.DummyConverter import DummyConverter
from domain.streamworkers.validators.DummyValidator import DummyValidator

class DomoticRoomBuilder(object):
    '''
    classdocs
    '''
    
    def __init__(self,address,port):
        '''
        Constructor
        '''
        self.sensorList = list()
        self.validatorList = list()
        self.converterList = list()
        self.domoticPackager=Packager("Domotic_Room_Packager")
        self.domoticSender=Sender("Domotic_Room-Sender")
        self.address=address
        self.port=port
        
    def initialize(self):
        #inizializzo la GPIO
        GPIO.setmode(GPIO.BOARD)
        self.socketD = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.domoticSender.setSocket(self.socketD)
        self.domoticSender.setAddress(self.address)
        self.domoticSender.setPort(self.port)
        self.domoticPackager.getOutputStream().subscribe(self.domoticSender)
    
    def setAddress(self,address):
        self.address = address
        self.domoticSender.setAddress(address)
          
    def addSensor(self,nome,tipo,pin,delay=None):
            if tipo == SensorType.Temperature_DHT11:
                s = TemperatureSensor(nome,tipo,pin,delay)
                v = TempSensorValidator("validator")
                s.getOutputStream().subscribe(v)
                c = TempConverter("converter")
                v.getOutputStream().subscribe(c)
            else :
                s = BooleanSensor(nome,tipo,pin)
                v = DummyValidator("validator")
                s.getOutputStream().subscribe(v)
                c = DummyConverter("converter")
                v.getOutputStream().subscribe(c)
            
            c.getOutputStream().subscribe(self.domoticPackager)    
            self.sensorList.append(s)
            self.validatorList.append(v)
            self.converterList.append(c)
            
    def start(self):
        for s in self.sensorList:
            s.start()
    
    def stop(self):
        for s in self.sensorList:
            s.stop()
            s.join(None)
        print("tutti gli thread completati")