package interfaces.dataInput

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/16/16.
  */
trait IDataReceiver {
  def getStream(data: JsValue) : Enumerator[JsValue]
}
