package interfaces.managers

import models.DataStructures.SensorModel.SensorType
import play.api.libs.json.JsValue
import models.DataStructures.RangeModel._

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IRangeChecker {
  def updateRange(rangeBoolean: RangeBoolean)

  def checkRange(data:JsValue,sensorType:SensorType.Value)
  def updateRange(range:Range)
}
