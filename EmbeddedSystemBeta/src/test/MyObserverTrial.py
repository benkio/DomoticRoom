'''
Created on 23/gen/2016

@author: nicola
'''
from rx.observer import Observer

class MyObserver(Observer):
    def on_next(self, x):
        name = x.getName()
        typeSensor = x.getTypeSensor()
        val = x.getVal()
        print("Sensore "+ str(name)+" del tipo "+str(typeSensor)+" ha dato valore: "+str(val))
        
    def on_error(self, e):
        print("Got error: %s" % e)
        
    def on_completed(self):
        print("Sequence completed")