package interfaces.managers

import models.DataStructures.{SensorType, Range}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IRangeChecker {
  def checkRange(data:JsValue,sensorType:SensorType.Value)
  def updateRange(range:Range, sensorType: SensorType.Value)
}
