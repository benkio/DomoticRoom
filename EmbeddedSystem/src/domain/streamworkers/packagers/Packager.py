'''
Created on 24/gen/2016

@author: nicola
'''
import json
from domain.streamworkers.AStreamWorker import AStreamWorker

class Packager(AStreamWorker):
    '''
    classdocs
    '''


    def __init__(self, name):
        '''
        Constructor
        '''
        AStreamWorker.__init__(self, name)
        
    def on_next(self,x):
        nameS = x.getName()
        typeS = x.getTypeSensor()
        dateS = x.getRelevationTime().strftime('%Y-%m-%d %H:%M:%S.')
        microS = str(x.getRelevationTime().microsecond)[0:3]
        dateS = dateS + microS
        valS  = x.getValore()        
        valoreFinale = json.dumps({'sensorName': nameS, 'sensorType': typeS, 'value':valS,'date':dateS}, separators=(',',':'))
	#valoreFinale = json.dumps([{'nomeSensore': nameS, 'dataRel':dateS}], separators=(',',':'))
	#print("LOG>" + valoreFinale)
        x.setValore(valoreFinale)
        self.outputStream.on_next(x)
        
    def on_error(self, error):
        self.outputStream.on_error(error)
        
    def on_completed(self):
        self.outputStream.on_completed()