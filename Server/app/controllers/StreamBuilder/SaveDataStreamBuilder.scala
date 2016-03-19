package controllers.StreamBuilder

import controllers.managers.EventManager
import interfaces.streamBuilder.IDataSaveStreamBuilder
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson
import play.api.libs.iteratee.{Enumeratee, Enumerator, Iteratee}
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by root on 3/13/16.
  */
object SaveDataStreamBuilder extends IDataSaveStreamBuilder {

  override def buildDataDoubleSaveStream(stream : Enumerator[JsValue]): Future[Iteratee[DataDBJsonModel[Double], Unit]] = {
    (stream &>
      Enumeratee.filter(x => {
        DataReceivedJson.validateReceivedDataDoubleJson(x).isSuccess
      }) &>
      Enumeratee.map(y => DataReceivedJson.validateReceivedDataDoubleJson(y).get) &>
      EventManager.newDataDouble )(Iteratee.foreach[DataDBJsonModel[Double]](z => println(z)))/* &>
      Enumeratee.map(y => DataDBJson.DoubleDataJsonModeltoBsonDocument(y)))(Iteratee.foreach[BSONDocument](z => {
        System.out.println("Save New Data")
        PersistenceStore.saveData(z)
      }))*/
  }

  override def buildDataBooleanSaveStream(stream : Enumerator[JsValue]): Future[Iteratee[DataDBJsonModel[Boolean], Unit]] = {
    (stream &>
      Enumeratee.filter(x => {
        DataReceivedJson.validateReceivedDataBooleanJson(x).isSuccess
      }) &>
      Enumeratee.map(y => DataReceivedJson.validateReceivedDataBooleanJson(y).get) &>
      EventManager.newDataBoolean)(Iteratee.foreach[DataDBJsonModel[Boolean]](z => println(z)))/*  &>
      Enumeratee.map(y => DataDBJson.BooleanDataJsonModeltoBsonDocument(y)))(Iteratee.foreach[BSONDocument](z => {
        System.out.println("Save New Data")
        PersistenceStore.saveData(z)
      }))*/
  }
}
