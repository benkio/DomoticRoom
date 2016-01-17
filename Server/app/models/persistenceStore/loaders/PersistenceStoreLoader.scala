package models.persistenceStore.loaders

import interfaces.presistenceStore.{IPersistenceStoreRangeLoader, IPersistenceStoreDataLoader, IPersistenceStoreLoader, IPersistenceStoreSensorsLoader}
import models.SensorType
import org.joda.time.{Interval, DateTime}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreLoader(psdl:IPersistenceStoreDataLoader, psrl:IPersistenceStoreRangeLoader, pssl:IPersistenceStoreSensorsLoader) extends IPersistenceStoreLoader {

  ///////////////////////////////////////
  ////////////Range Load Operations//////
  ///////////////////////////////////////
  override def loadRange(sensorType: SensorType, startDate: DateTime, timeInterval: Interval): Unit =
    psrl.loadRange(sensorType, startDate, timeInterval)

  override def loadLastRange(sensorType: SensorType): Unit =
    psrl.loadLastRange(sensorType)

  override def loadLastRanges: Unit =
    psrl.loadLastRanges

  ///////////////////////////////////////
  ////////////Data Load Operations///////
  ///////////////////////////////////////

  override def loadCurrentSensorsData(): Unit =
    psdl.loadCurrentSensorsData()

  override def loadData(sensorName: String, startDate: DateTime, timeInterval: Interval): Unit =
    psdl.loadData(sensorName,startDate,timeInterval)

  override def loadCurrentSensorData(sensorName: String): Unit =
    psdl.loadCurrentSensorData(sensorName)

  ///////////////////////////////////////
  ////////////Sensorss Load Operations///
  ///////////////////////////////////////

  override def loadSensors: Unit =
    pssl.loadSensors
}
