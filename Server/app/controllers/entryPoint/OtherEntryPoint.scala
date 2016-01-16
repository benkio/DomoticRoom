package controllers.entryPoint

import play.api.mvc._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class OtherEntryPoint extends Controller{
  def root = Action {
    Redirect(controllers.entryPoint.routes.StatusEntryPoint.status)
  }
}
