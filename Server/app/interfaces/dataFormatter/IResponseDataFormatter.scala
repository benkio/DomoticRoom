package interfaces.dataFormatter

import play.api.libs.iteratee.{Iteratee, Enumeratee, Enumerator}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IResponseDataFormatter extends IDataFormatter{
  def getFormatterStreamStep
}
