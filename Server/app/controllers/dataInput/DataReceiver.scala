package controllers.dataInput

import akka.actor.{Props, Actor}
import akka.event.LoggingReceive
import interfaces.dataInput.IDataReceiver
import play.api.libs.iteratee.{Concurrent, Enumerator}
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global

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

    val dataEnumerator = Concurrent.unicast[JsValue](
      // you cannot change actor state from in here, it is on another thread
      onStart = channel => self ! GotChannel(channel),
      onComplete = () => self ! ChannelClosed,
      onError = (msg, _) => self ! ChannelError(msg)
    )

    def receive = {
      case GotChannel(channel) =>
        context.become(active(channel))
      case "getStream" => sender() ! dataEnumerator
    }

    def active(channel: Concurrent.Channel[JsValue]): Actor.Receive = LoggingReceive {
      case message: JsValue =>
        System.out.println("Push data to stream " + message)
        // push the message to the stream
        channel.push(message)

      // handle ChannelClosed and ChannelError here

    }
  }
}