package interfaces.dataInput

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IDataReceiver {
  def getStream(data: JsValue) : Enumerator[JsValue]
}
