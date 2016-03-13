package controllers.StreamBuilder

import controllers.dataInput.DataReceiver
import controllers.managers.EventManager
import interfaces.streamBuilder.IDataSaveStreamBuilder

import models.DataStructures.{DataDBJson, DataReceivedJson}
import scala.concurrent.ExecutionContext.Implicits.global
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by root on 3/13/16.
  */
object SaveDataStreamBuilder extends IDataSaveStreamBuilder {
  override def buildDataSaveStream(): Future[Iteratee[BSONDocument, Unit]] = {
    (DataReceiver.getStream &>
      Enumeratee.filter(x => DataReceivedJson.validateJsValueToReceivedDataJsonModel(x)) &>
      Enumeratee.map(y => DataReceivedJson.readJsValueToReceivedDataJsonModel(y).get) &>
      EventManager.newData &>
      Enumeratee.map(z => DataDBJson.toBsonDocument(z)))(Iteratee.foreach[BSONDocument](z => PersistenceStore.saveData(z)))
  }
}
