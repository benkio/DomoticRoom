package controllers.entryPoint

import play.api.mvc.{Action, Controller}
import views.html._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class StatusEntryPoint extends Controller{
  def status = Action {Ok(statusView.render)}
}
