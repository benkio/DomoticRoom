'''
Created on 24/gen/2016

@author: nicola
'''
from rx.observer import Observer

class Sender(Observer):
    
    def __init__(self,name):
         
        Observer.__init__(self)       
            
    def on_next(self, x):
        message = x.getValore()
        #test
        #print("Log_sender_data_finali: "+message)
        self.socket.sendto(message,self.address)
        print("dato inviato")
        
    def on_error(self, e):
        pass
        
    def on_completed(self):
        self.socket.close()
        
    def setSocket(self,socket):
        self.socket=socket
    
    def setAddress(self,address):
        self.address=address