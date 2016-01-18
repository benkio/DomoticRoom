package interfaces.presistenceStore

import models.SensorType
import play.api.libs.json.JsValue
import play.libs.F.Tuple

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreDataSaver {
  def save(data: JsValue, sensorName: String, sensorType: SensorType)
  def saveWithRangeException(data: JsValue, sensorName: String, range: models.Range, sensorType: SensorType, delta: Double)
}
