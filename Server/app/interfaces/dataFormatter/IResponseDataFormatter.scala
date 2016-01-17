package interfaces.dataFormatter

import play.api.libs.iteratee.{Iteratee, Enumeratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/17/16.
  */
trait IResponseDataFormatter extends IDataFormatter{
  override def getFormattedStream(source:Enumerator[JsValue]):Enumeratee[JsValue,JsValue]
}
