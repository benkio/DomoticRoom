package views.presentator

import interfaces.presentator.IPresentator
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class Presentator extends IPresentator{
  override def presentData(stream: Iteratee[JsValue, JsValue]) = ??? //TODO
}
