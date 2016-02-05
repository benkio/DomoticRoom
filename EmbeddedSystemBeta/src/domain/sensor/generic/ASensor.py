'''
Created on 23/gen/2016

@author: Enrico Benini, Nicola Casadei, Marco Benedetti
'''

from rx.subjects.subject import Subject
from threading import Thread
from RPi import GPIO
from multiprocessing.synchronize import Event

class ASensor(Thread):
    '''
    La classe ASensor rappresenta un sensore generico, in essa sono implementati:
    gli attributi, le configurazioni iniziali comuni a qualunque sensore.
    Ogni sensore e' uno Thread in modo tale che il suo flusso di controllo sia indipendente.
    '''


    def __init__(self, name,typeSensor,pin):
        '''
        il costruttuore accetta in ingresso:
        un nome per identificare il sensore, il tipo del sensore
        e il PIN al quale e' collegato sul Raspberry.
        
        Viene creato un oggetto di tipo Subject che rappresenta lo Stream di valori in uscita dal sensore.
        Necessario per implementare il paradigma reactive
        '''
        Thread.__init__(self, None, None, name, None, None, None)
        self.name=name
        self.typeSensor=typeSensor
        self.pin=pin
        self.flag = Event()
        self.stream=Subject()
        
    '''
    Classi che verranno implementate dai singoli sensori
    '''
    
    def getValue(self):
        pass
    
    def getName(self):
        return self.name
    
    def getType(self):
        return self.typeSensor
    
    def getPin(self):
        return self.pin
    
    def getOutputStream(self):
        return self.stream
        
    '''
    la funzione initialize configura il PIN per poterlo utilizzare.
    Questa procedura e' comune a tutti i sensori (con l'utilizzo della libreria RPi.GPIO)
    '''
     
    def stop(self):
        self.flag.set()