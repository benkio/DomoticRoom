package controllers.managers

import interfaces.managers.IRangeChecker
import models.{SensorType, Range}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class RangeChecker extends IRangeChecker{
  val currentRanges : Map[SensorType,Range] = ???
  override def checkRange(data: JsValue, sensorType: SensorType): Unit = ??? //TODO

  override def updateRange(range: Range, sensorType: SensorType): Unit = ??? //TODO
}
