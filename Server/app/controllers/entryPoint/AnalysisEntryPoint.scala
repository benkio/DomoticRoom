package controllers.entryPoint

import controllers.StreamBuilder.LoadDataStreamBuilder
import play.api.mvc._
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import controllers.StreamBuilder.StreamUtils
import models.DataStructures.DataDBJson.{DataAnalizeDBJson, DataAnalizeDBJsonMerged}
import views.html

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class AnalysisEntryPoint extends Controller{
  def analysis = Action {

    var analisysStream = LoadDataStreamBuilder.getDataMininum.andThen(LoadDataStreamBuilder.getDataMaxinum).andThen(LoadDataStreamBuilder.getDataAverage)
    // TODO: add an iteratee that reduce the enumerator to an array of json, and pass all to the page. The page analyse the responce and fullfill the view.
    val analisysData : Future[Seq[DataAnalizeDBJsonMerged]] = StreamUtils.runIterateeFuture (analisysStream &> Enumeratee.filter(x => x.dataType != 0) |>> Iteratee.fold[DataAnalizeDBJson,Seq[DataAnalizeDBJsonMerged]](Seq())((sequence:Seq[DataAnalizeDBJsonMerged], elem:DataAnalizeDBJson) => {
      if (sequence.exists(x => x.dataType == elem.dataType)) {
        val y : Seq[DataAnalizeDBJsonMerged] = sequence.patch(sequence.indexWhere(x => x.dataType == elem.dataType), Seq(DataAnalizeDBJsonMerged(elem.dataType, sequence(elem.dataType).keyValue.:+(elem.analisysType, elem.value))) ,1)
        y
      } else {
        sequence.:+(DataAnalizeDBJsonMerged(elem.dataType,List((elem.analisysType,elem.value))))
      }
    }))
    Ok(views.html.analisys.render(Await.result(analisysData,30.seconds)))
  }
}
