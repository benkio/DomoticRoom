'''
Created on 23/gen/2016

@author: nicola
'''

from domain.sensorType.SensorType import SensorType
from domain.factory.DomoticRoomBuilder import DomoticRoomBuilder

if __name__ == '__main__':
    pass

print("Configurazione guidata della DomoticRoom in corso...")
addressIP = raw_input("Inserire IP del Server:")
portIP = raw_input("Inserire porta del Server:")
#address = (addressIP,int(portIP))
room = DomoticRoomBuilder(addressIP,int(portIP))
room.addSensor("DHT11", SensorType.Temperature_DHT11, 7, 5)
room.addSensor("UUGear_LightSensor",SensorType.Light,11)

while True:
    answer = raw_input("Vuoi iniziare il monitoraggio ? [s\\n]")
    if(answer == 's'):
        room.initialize()
        room.start()
        answer1= raw_input("Digitare   stop   quando si vuole terminare...")
        if(answer1 == 'stop'):
            room.stop()
    else :
        exit()
        break