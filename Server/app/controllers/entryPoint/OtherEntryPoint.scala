package controllers.entryPoint

import play.api.libs.json.JsValue
import play.api.mvc._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class OtherEntryPoint extends Controller{
  def root = Action {
    Redirect(controllers.entryPoint.routes.StatusEntryPoint.status)
  }

  def echo = Action { implicit request =>
    val content = request.body.asJson
    content match {
      case Some(x)  => Ok(x)
      case None     => BadRequest("the content of the request is not a Json Value")
    }
  }
}
