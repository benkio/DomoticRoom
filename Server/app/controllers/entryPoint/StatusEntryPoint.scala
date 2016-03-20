package controllers.entryPoint

import akka.actor.ActorSystem
import com.google.inject.{Inject, Singleton}
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller}
import views.html._
import controllers.dataInput._
import controllers.StreamBuilder._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
import akka.pattern.ask


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

  def status = Action {Ok(statusView.render)}

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
