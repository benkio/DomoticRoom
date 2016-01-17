package views.presentator

import interfaces.presentator.IPresentator
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/17/16.
  */
class Presentator extends IPresentator{
  override def presentData(stream: Iteratee[JsValue, JsValue]) = ??? //TODO
}
