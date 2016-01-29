package interfaces.managers

import models.DataStructures.SensorType
import play.api.libs.iteratee.{Iteratee, Enumeratee}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IEventManager {
  def newData(enumeratee:Enumeratee[JsValue,JsValue]):Enumeratee[JsValue,JsValue]
  def newRange(range:Range, sensorType:SensorType.Value) :Iteratee[JsValue,JsValue]
}
