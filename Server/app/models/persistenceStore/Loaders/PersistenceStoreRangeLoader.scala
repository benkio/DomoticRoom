package models.persistenceStore.Loaders

import Interfaces.PresistenceStore.IPersistenceStoreRangeLoader
import models.SensorType
import org.joda.time.{Interval, DateTime}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeLoader extends IPersistenceStoreRangeLoader{
  override def loadRange(sensorType: SensorType, startDate: DateTime, timeInterval: Interval): Unit = ??? //TODO

  override def loadLastRange(sensorType: SensorType): Unit = ??? //TODO

  override def loadLastRanges: Unit = ??? //TODO
}
