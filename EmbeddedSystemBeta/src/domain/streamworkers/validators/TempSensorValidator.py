'''
Created on 26/gen/2016

@author: nicola
'''
import RPi.GPIO as GPIO
from domain.streamworkers.AStreamWorker import AStreamWorker
from domain.sensorType.SensorType import SensorType

class TempSensorValidator(AStreamWorker):
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
            sensorData = x.getValore()
            
            pull_up_lengths = self.__parseDataPullUp(sensorData)
            
            if (len(pull_up_lengths)) == 40:
                #go-on
                data_bits = self.__calculateBits(pull_up_lengths)
                
                data_bytes= self.__bitsToBytes(data_bits)
                
                checksum = self.__calculateChecksum(data_bytes)
                
                if data_bytes[4] == checksum :
                    x.setValore(data_bytes)
                    #print("dati inviati")
                    #print("".join(str(x) for x in data_bytes))
                    self.outputStream.on_next(x)
        
    def on_error(self, error):
        self.outputStream.on_error(error)
        
    def on_completed(self):
        self.outputStream.on_completed()
        
    def __parseDataPullUp(self,data):
        STATE_INIT_PULL_DOWN = 1
        STATE_INIT_PULL_UP = 2
        STATE_DATA_FIRST_PULL_DOWN = 3
        STATE_DATA_PULL_UP = 4
        STATE_DATA_PULL_DOWN = 5
        
        state = STATE_INIT_PULL_DOWN
        
        lengths = []
        current_length = 0
        
        for i in range(len(data)):
            current = data[i]
            current_length += 1
            
            if state == STATE_INIT_PULL_DOWN:
                if current == GPIO.LOW:
                    # ok, we got the initial pull down
                    state = STATE_INIT_PULL_UP
                    continue
                else:
                    continue
            
            if state == STATE_INIT_PULL_UP:
                if current == GPIO.HIGH:
                    # ok, we got the initial pull up
                    state = STATE_DATA_FIRST_PULL_DOWN
                    continue
                else:
                    continue
            
            if state == STATE_DATA_FIRST_PULL_DOWN:
                if current == GPIO.LOW:
                    # we have the initial pull down, the next will be the data pull up
                    state = STATE_DATA_PULL_UP
                    continue
                else:
                    continue

            if state == STATE_DATA_PULL_UP:
                if current == GPIO.HIGH:
                    # data pulled up, the length of this pull up will determine whether it is 0 or 1
                    current_length = 0
                    state = STATE_DATA_PULL_DOWN
                    continue
                else:
                    continue
            
            if state == STATE_DATA_PULL_DOWN:
                if current == GPIO.LOW:            
                    # pulled down, we store the length of the previous pull up period
                    lengths.append(current_length)
                    state = STATE_DATA_PULL_UP                
                    continue
                else:
                    continue
                    
        return lengths
    
    def __calculateBits(self,pull_up_lengths):
        # find shortest and longest period
        shortest_pull_up = 1000
        longest_pull_up = 0
        
        for i in range(0, len(pull_up_lengths)):
                
            length = pull_up_lengths[i]
            if length < shortest_pull_up:
                shortest_pull_up = length
                
            if length > longest_pull_up:
                longest_pull_up = length
                
        # use the halfway to determine whether the period it is long or short
        halfway = shortest_pull_up + (longest_pull_up - shortest_pull_up) / 2
        
        bits = []
        
        for i in range(0, len(pull_up_lengths)):
            
            bit = False
            if pull_up_lengths[i] > halfway:
                bit = True
                
            bits.append(bit)
        
        return bits
    
    def __bitsToBytes(self,bits):
        
        dataBytes = []
        byte = 0
        
        for i in range(0, len(bits)):
            
            byte = byte << 1
            if (bits[i]):
                byte = byte | 1
            else:
                byte = byte | 0
            
            if ((i + 1) % 8 == 0):
                dataBytes.append(byte)
                byte = 0
                
        return dataBytes
    
    def __calculateChecksum(self,databytes):
        return databytes[0] + databytes[1] + databytes[2] + databytes[3] & 255