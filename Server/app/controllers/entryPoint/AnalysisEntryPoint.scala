package controllers.entryPoint

import controllers.StreamBuilder.LoadDataStreamBuilder
import play.api.mvc._
import models.persistenceStore.PersistenceStore
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class AnalysisEntryPoint extends Controller{
  def analysis = Action {

    var analisysStream = LoadDataStreamBuilder.getDataMininum.andThen(LoadDataStreamBuilder.getDataMaxinum).andThen(LoadDataStreamBuilder.getDataAverage)
    // TODO: add an iteratee that reduce the enumerator to an array of json, and pass all to the page. The page analyse the responce and fullfill the view.

    Ok(views.html.index.render("test"))
  }
}
