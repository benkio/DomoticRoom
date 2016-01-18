package models.persistenceStore.savers

import interfaces.presistenceStore.IPersistenceStoreRangeSaver
import models.{SensorType, Range}
import reactivemongo.api._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeSaver(connection:DB) extends IPersistenceStoreRangeSaver{
  override def save(range: Range, sensorType: SensorType): Unit = ??? //TODO
}
