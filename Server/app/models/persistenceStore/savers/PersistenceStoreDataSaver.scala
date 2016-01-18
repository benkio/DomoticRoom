package models.persistenceStore.savers

import interfaces.presistenceStore.IPersistenceStoreDataSaver
import models.{SensorType, Range}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataSaver extends IPersistenceStoreDataSaver{
  override def save(data: JsValue, sensorName: String, sensorType: SensorType): Unit = ??? //TODO

  override def saveWithRangeException(data: JsValue, sensorName: String, range: Range, sensorType: SensorType, delta: Double): Unit = ??? //TODO
}
