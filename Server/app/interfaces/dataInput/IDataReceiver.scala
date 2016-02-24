package interfaces.dataInput

import play.api.libs.iteratee.{Concurrent, Enumerator}
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IDataReceiver {
  def getStream(data: JsValue) : Enumerator[JsValue]

  def buildEnumerator(data: JsValue) = {

    val newDataEnumerator = Concurrent.unicast[JsValue](onStart = channel =>{
      channel.push(data)
    })

    newDataEnumerator
  }
}
