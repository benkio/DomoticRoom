package controllers.managers

import interfaces.managers.IRangeChecker
import models.DataStructures.SensorType
import models.{DataStructures, SensorType}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class RangeChecker extends IRangeChecker{
  val currentRanges : Map[SensorType.Value,DataStructures.Range] = ???
  override def checkRange(data: JsValue, sensorType: SensorType.Value): Unit = ??? //TODO

  override def updateRange(range: DataStructures.Range, sensorType: SensorType.Value): Unit = ??? //TODO
}
