package models.persistenceStore.loaders

import interfaces.presistenceStore.{IPersistenceStoreRangeLoader, IPersistenceStoreDataLoader, IPersistenceStoreLoader, IPersistenceStoreSensorsLoader}
import models.{RangeType, SensorType}
import org.joda.time.{ReadableDuration, Interval, DateTime}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreLoader(psdl:IPersistenceStoreDataLoader, psrl:IPersistenceStoreRangeLoader, pssl:IPersistenceStoreSensorsLoader) extends IPersistenceStoreLoader {

  ///////////////////////////////////////
  ////////////Range Load Operations//////
  ///////////////////////////////////////
  override def loadRange(rangeType: RangeType, startDate: DateTime, duration:ReadableDuration): Unit =
    psrl.loadRange(rangeType, startDate, duration)

  override def loadLastRange(sensorType: SensorType): Unit =
    psrl.loadLastRange(sensorType)

  override def loadLastRanges: Unit =
    psrl.loadLastRanges

  ///////////////////////////////////////
  ////////////Data Load Operations///////
  ///////////////////////////////////////

  override def loadCurrentSensorsData():  Future[List[BSONDocument]] =
    psdl.loadCurrentSensorsData()

  override def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration): Future[List[BSONDocument]] =
    psdl.loadData(sensorName,startDate,duration)

  override def loadCurrentSensorData(sensorName: String):  Future[List[BSONDocument]] =
    psdl.loadCurrentSensorData(sensorName)

  ///////////////////////////////////////
  ////////////Sensorss Load Operations///
  ///////////////////////////////////////

  override def loadSensors: Unit =
    pssl.loadSensors
}
