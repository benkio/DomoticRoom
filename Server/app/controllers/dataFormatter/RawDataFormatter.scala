package controllers.dataFormatter

import interfaces.dataFormatter.IRawDataFormatter
import play.api.libs.iteratee.{Iteratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class RawDataFormatter extends IRawDataFormatter{
  override def format(data: JsValue): JsValue = ??? //TODO

  override def getFormatterStreamStep = ??? //TODO
}
