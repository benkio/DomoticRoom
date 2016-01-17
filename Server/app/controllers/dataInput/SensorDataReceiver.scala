package controllers.dataInput

import interfaces.dataInput.IDataReceiver
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class SensorDataReceiver extends IDataReceiver{
  override def getStream(data: JsValue): Enumerator[JsValue] = ??? //TODO
}
