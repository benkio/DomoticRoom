package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreSensorsLoader
import reactivemongo.api._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreSensorsLoader(connection:DB) extends IPersistenceStoreSensorsLoader{
  override def loadSensors: Unit = ??? //TODO
}
