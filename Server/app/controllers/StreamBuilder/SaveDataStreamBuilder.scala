package controllers.StreamBuilder

import controllers.managers.EventManager
import interfaces.streamBuilder.IDataSaveStreamBuilder
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.{DataDBJson, DataReceivedJson}
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Enumeratee, Enumerator, Iteratee}
import play.api.libs.json.JsValue
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by root on 3/13/16.
  */
object SaveDataStreamBuilder extends IDataSaveStreamBuilder {

  override def buildDataDoubleSaveStream(stream : Enumerator[JsValue]): Future[Iteratee[BSONDocument, Unit]] = {
    (stream &>
      Enumeratee.filter(x => {
        DataReceivedJson.validateReceivedDataDoubleJson(x).isSuccess
      }) &>
      Enumeratee.map(y => DataReceivedJson.validateReceivedDataDoubleJson(y).get) &>
      EventManager.newDataDouble  &>
      Enumeratee.map(y => DataDBJson.DataJsonModelToBson(y)))(Iteratee.foreach[BSONDocument](z => PersistenceStore.saveData(z)))
  }

  override def buildDataBooleanSaveStream(stream : Enumerator[JsValue]): Future[Iteratee[BSONDocument, Unit]] = {
    (stream &>
      Enumeratee.filter(x => {
        DataReceivedJson.validateReceivedDataBooleanJson(x).isSuccess
      }) &>
      Enumeratee.map(y => DataReceivedJson.validateReceivedDataBooleanJson(y).get) &>
      EventManager.newDataBoolean  &>
      Enumeratee.map(y => DataDBJson.DataJsonModelToBson(y)))(Iteratee.foreach[BSONDocument](z => PersistenceStore.saveData(z)))
  }
}
