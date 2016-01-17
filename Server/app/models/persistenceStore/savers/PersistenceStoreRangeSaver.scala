package models.persistenceStore.savers

import interfaces.presistenceStore.IPersistenceStoreRangeSaver
import models.{SensorType, Range}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeSaver extends IPersistenceStoreRangeSaver{
  override def save(range: Range, sensorType: SensorType): Unit = ??? //TODO
}
