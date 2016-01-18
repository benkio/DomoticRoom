package controllers.entryPoint

import play.api.mvc._
import views.html._
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class AnalysisEntryPoint extends Controller{
  def analysis = Action {
    Ok(index.render("test"))
  }
}
