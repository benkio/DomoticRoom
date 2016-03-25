package controllers.entryPoint

import akka.actor.ActorSystem
import akka.pattern.ask
import com.google.inject.{Inject, Singleton}
import controllers.StreamBuilder
import controllers.StreamBuilder._
import controllers.dataInput._
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.{Concurrent, Enumeratee, Enumerator, Iteratee}
import play.api.libs.json.{JsString, JsValue}
import play.api.mvc.{Action, Controller, WebSocket}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
@Singleton
class StatusEntryPoint @Inject() (system: ActorSystem) extends Controller{
  implicit val timeout : akka.util.Timeout = 5.seconds
  val duration = 5.seconds
  val dataReceiverActor = system.actorOf(DataReceiver.props, "dataReceiverDoubleActor")

  val stream = (dataReceiverActor ? "getStream").mapTo[Enumerator[JsValue]]

  val saveDataStream = SaveDataStreamBuilder.buildDataDoubleSaveStream(Await.result(stream,duration))
  val saveDataBooleanStream = SaveDataStreamBuilder.buildDataBooleanSaveStream(Await.result(stream,duration))

  def status = Action {Ok(views.html.statusView.render)}

  def dataStream = WebSocket.using[JsValue]{ request =>

    // Concurrent.broadcast returns (Enumerator, Concurrent.Channel)
    val (outRequestEnumerator, channel) = Concurrent.broadcast[JsValue]

    val out : Enumerator[JsValue] = LoadDataStreamBuilder.getDataStreamSingle() >- outRequestEnumerator //LoadDataStreamBuilder.getDataStream(30.seconds)

    // log the message to stdout and send response back to client
    val in = Iteratee.foreach[JsValue] {
      msg => println(msg)
        // the Enumerator returned by Concurrent.broadcast subscribes to the channel and will
        // receive the pushed messages
        LoadDataStreamBuilder.getDataStreamSingle()(Iteratee.foreach(x =>{
          channel push(x)
        }))
    }
    (in,out)
  }

  def submitNewData = Action { implicit request =>
    val content = request.body.asJson
    content match {
      case Some(x)  => {
          dataReceiverActor   ! x
          Ok
      }
      case None     => BadRequest("the content of the request is not a Json Value")
    }
  }
}
