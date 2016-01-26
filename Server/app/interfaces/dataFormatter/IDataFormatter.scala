package interfaces.dataFormatter

import play.api.libs.iteratee.{Enumeratee, Iteratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IDataFormatter {
  def format(data:JsValue):JsValue
  def getFormatterStreamStep :Enumeratee[JsValue,JsValue]
}
