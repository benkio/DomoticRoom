package controllers.dataInput

import akka.actor.{Actor, Props}
import interfaces.dataInput.IDataReceiver
import play.api.libs.iteratee.Concurrent
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object DataReceiver {

  def props = Props[DataReceiverActor]

  class DataReceiverActor extends Actor with IDataReceiver {

    // WorkerActor
    case class GotChannel(channel: Concurrent.Channel[JsValue])
    case object ChannelClosed

    case class ChannelError(msg: String)

    val (dataEnumerator,dataEnumeratorChannel) = Concurrent.broadcast[JsValue]

    def receive = {
      case message: JsValue =>
        System.out.println("Push data to stream " + message)
        // push the message to the stream
        dataEnumeratorChannel.push(message)

      case "getStream" => sender() ! dataEnumerator
    }
  }
}