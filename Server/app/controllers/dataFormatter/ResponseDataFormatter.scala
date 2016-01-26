package controllers.dataFormatter

import interfaces.dataFormatter.IResponseDataFormatter
import play.api.libs.iteratee.{Enumeratee, Iteratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class ResponseDataFormatter extends IResponseDataFormatter{
  override def format(data: JsValue): JsValue = ??? //TODO

  override def getFormatterStreamStep : Enumeratee[JsValue,JsValue] = ??? //TODO
}
