package controllers.dataFormatter

import interfaces.dataFormatter.IResponseDataFormatter
import play.api.libs.iteratee.{Iteratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/17/16.
  */
class ResponseDataFormatter extends IResponseDataFormatter{
  override def format(data: JsValue): JsValue = ??? //TODO

  override def getFormattedStream(source: Enumerator[JsValue]) = ??? //TODO
}
