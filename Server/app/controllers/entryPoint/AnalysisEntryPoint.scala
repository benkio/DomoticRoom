package controllers.entryPoint

import play.api.mvc._
import models.persistenceStore.PersistenceStore
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class AnalysisEntryPoint extends Controller{
  def analysis = Action {

    // TODO: call the streambuilder to get the jsEnumerator for every kind of analysis, check che interface

    Ok(views.html.index.render("test"))
  }
}
