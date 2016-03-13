package interfaces.dataInput

import play.api.libs.iteratee.{Concurrent, Enumerator}
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IDataReceiver[T] {
  def getStream : Enumerator[T]

  def sendDataToStream(data: T)
}
