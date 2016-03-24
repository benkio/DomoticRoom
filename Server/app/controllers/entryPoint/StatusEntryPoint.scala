package controllers.entryPoint

import akka.actor.ActorSystem
import akka.pattern.ask
import com.google.inject.{Inject, Singleton}
import controllers.StreamBuilder
import controllers.StreamBuilder._
import controllers.dataInput._
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.{Enumeratee, Enumerator, Iteratee}
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

    val out : Enumerator[JsValue] = /*Enumerator(JsString("hello")) */ LoadDataStreamBuilder.getDataStream(3.seconds)

    // log the message to stdout and send response back to client
    val in = Iteratee.ignore[JsValue]
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
