package interfaces.dataFormatter

import play.api.libs.iteratee.{Enumeratee, Iteratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/17/16.
  */
trait IDataFormatter {
  def format(data:JsValue):JsValue
  def getFormattedStream(source:Enumerator[JsValue]):Enumeratee[JsValue,JsValue]
}
