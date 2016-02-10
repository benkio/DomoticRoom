'''
Created on 23/gen/2016

@author: Enrico Benini, Nicola Casadei, Marco Benedetti
'''

from domain.sensorType.SensorType import SensorType
from domain.factory.DomoticRoomBuilder import DomoticRoomBuilder

if __name__ == '__main__':
    pass

print("Configurazione guidata della DomoticRoom in corso...")
addressIP = raw_input("Inserire IP del Server:")
portIP = raw_input("Inserire porta del Server:")
address = (addressIP,int(portIP))
room = DomoticRoomBuilder(address)
room.addSensor("temperatura", SensorType.Temperature_DHT11, 7, 5)
room.addSensor("luce",SensorType.Light,11)

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
    

'''
d = DomoticRoomBuilder('192.168.1.20',8081)
d.initialize()
tempSensor = TemperatureSensor("temperature", SensorType.Temperature_DHT11, 7,5)
gasSensor = BooleanSensor("gas", SensorType.Gas_MQ2,12)
lightSensor = BooleanSensor("luce", SensorType.Light,11)
validator = TempSensorValidator("validatore")
convert = TempConverter("convertitore")
packager = Packager("packager")
sender = Sender("sender",d.getSocket(),d.getIP(),d.getPort())



#validator.subscribeToInputStream(tempSensor)
tempSensor.getOutputStream().subscribe(validator)
gasSensor.getOutputStream().subscribe(packager)
lightSensor.getOutputStream().subscribe(packager)
validator.getOutputStream().subscribe(convert)
convert.getOutputStream().subscribe(packager)
packager.getOutputStream().subscribe(sender)

print("sensor_start")
tempSensor.start()
gasSensor.start()
lightSensor.start()

tempSensor.join(None)
gasSensor.join(None)
lightSensor.join(None)
'''