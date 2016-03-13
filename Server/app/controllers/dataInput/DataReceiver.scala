package controllers.dataInput

import interfaces.dataInput.IDataReceiver
import play.api.libs.iteratee.{Concurrent, Enumerator}
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object DataReceiver extends IDataReceiver[JsValue]{
  var channel : Concurrent.Channel[JsValue] = null
  val dataEnumerator = Concurrent.unicast[JsValue](onStart = channel =>{
    this.channel = channel
  })

  override def getStream : Enumerator[JsValue] = dataEnumerator

  override def sendDataToStream(data: JsValue): Unit = {
    channel.push(data)
  }
}
