package controllers.managers

import interfaces.managers.{IRangeChecker, IEventManager}
import models.DataStructures.RangeModel.RangeBoolean
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object EventManager extends IEventManager{
  def newRange(rangeBoolean: RangeBoolean) = rangeChecker.updateRange(rangeBoolean)

  val rangeChecker:IRangeChecker = new RangeChecker
  override def newData(enumeratee: Enumeratee[JsValue,JsValue]): Enumeratee[JsValue,JsValue] = ??? //TODO

  override def newRange(range: models.DataStructures.RangeModel.Range) = {
    rangeChecker.updateRange(range)
  }
}
