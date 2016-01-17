package controllers.managers

import interfaces.managers.{IRangeChecker, IEventManager}
import models.{SensorType, Range}
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object EventManager extends IEventManager{
  val rangeChecker:IRangeChecker = new RangeChecker
  override def newData(enumeratee: Enumeratee[JsValue,JsValue]): Enumeratee[JsValue,JsValue] = ??? //TODO

  override def newRange(range: Range, sensorType: SensorType): Iteratee[JsValue,JsValue] = ??? //TODO
}
