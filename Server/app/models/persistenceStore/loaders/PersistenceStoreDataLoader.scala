package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import org.joda.time.{Interval, DateTime}
import reactivemongo.api._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataLoader(connection:DB) extends IPersistenceStoreDataLoader{
  override def loadData(sensorName: String, startDate: DateTime, timeInterval: Interval): Unit = ??? //TODO

  override def loadCurrentSensorsData(): Unit = ??? //TODO

  override def loadCurrentSensorData(sensorName: String): Unit = ??? //TODO
}
