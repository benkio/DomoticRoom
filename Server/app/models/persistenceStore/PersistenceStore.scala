package models.persistenceStore

import Interfaces.PresistenceStore.{IPersistenceStoreLoader, IPersistenceStoreSaver, IPersistenceStore}
import models.{SensorType, Range}
import org.joda.time.{Interval, DateTime}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStore(pss: IPersistenceStoreSaver, psl : IPersistenceStoreLoader ) extends IPersistenceStore {

  //////////////////////////////////////////////
  ////////////////Load Operations///////////////
  //////////////////////////////////////////////

  override def loadData(sensorName: String, startDate: DateTime, timeInterval: Interval): Unit =
    psl.loadData(sensorName, startDate, timeInterval)

  override def loadCurrentSensorsData(): Unit =
    psl.loadCurrentSensorsData()

  override def loadCurrentSensorData(sensorName: String): Unit =
    psl.loadCurrentSensorData(sensorName)

  override def loadLastRange(sensorType: SensorType): Unit =
    psl.loadLastRange(sensorType)

  override def loadRange(sensorType: SensorType, startDate: DateTime, timeInterval: Interval): Unit =
    psl.loadRange(sensorType, startDate, timeInterval)

  override def loadLastRanges: Unit =
    psl.loadLastRanges

  override def loadSensors: Unit =
    psl.loadSensors

  //////////////////////////////////////////////
  ////////////////Save Operations///////////////
  //////////////////////////////////////////////

  override def saveWithRangeException(data: JsValue, sensorName: String, range: Range, sensorType: SensorType, delta: Double): Unit =
    pss.saveWithRangeException(data, sensorName, range, sensorType, delta)

  override def save(data: JsValue, sensorName: String, sensorType: SensorType): Unit =
    pss.save(data,sensorName,sensorType)

  override def save(range: Range, sensorType: SensorType): Unit =
    pss.save(range,sensorType)
}
