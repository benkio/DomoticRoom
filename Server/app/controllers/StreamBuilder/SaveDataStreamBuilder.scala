package controllers.StreamBuilder

import controllers.dataInput.DataReceiver
import controllers.managers.EventManager
import interfaces.streamBuilder.IDataSaveStreamBuilder
import models.DataStructures.DataReceivedJson.DataReceivedJsonModel
import models.DataStructures.{DataDBJson, DataReceivedJson}
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.Reads._
import scala.concurrent.Future

/**
  * Created by root on 3/13/16.
  */
object SaveDataStreamBuilder extends IDataSaveStreamBuilder {

  override def buildDataDoubleSaveStream: Future[Iteratee[BSONDocument, Unit]] = {
    (DataReceiver.getStream &>
      Enumeratee.filter(x => DataReceivedJson.DataReceivedJsonModelReader(DoubleReads).reads(x).isSuccess) &>
      Enumeratee.map(y => DataReceivedJson.DataReceivedJsonModelReader(DoubleReads).reads(y).get) &>
      Enumeratee.filter(x => x.value.isInstanceOf[Double]) &>
      Enumeratee.map[DataReceivedJsonModel[Double]](y => DataReceivedJsonModel[Double](y.sensorName,y.dateCreation,y.value,y.dataType)) &>
      EventManager.newData[Double] &>
      Enumeratee.map(y => DataDBJson.DoubleDataJsonModeltoBsonDocument(y)))(Iteratee.foreach[BSONDocument](z => PersistenceStore.saveData(z)))
  }

  override def buildDataBooleanSaveStream: Future[Iteratee[BSONDocument, Unit]] = {
    (DataReceiver.getStream &>
      Enumeratee.filter(x => DataReceivedJson.DataReceivedJsonModelReader(BooleanReads).reads(x).isSuccess) &>
      Enumeratee.map(y => DataReceivedJson.DataReceivedJsonModelReader(BooleanReads).reads(y).get) &>
      Enumeratee.filter(x => x.value.isInstanceOf[Boolean]) &>
      Enumeratee.map[DataReceivedJsonModel[Boolean]](y => DataReceivedJsonModel[Boolean](y.sensorName,y.dateCreation,y.value,y.dataType)) &>
      EventManager.newData[Boolean] &>
      Enumeratee.map(y => DataDBJson.BooleanDataJsonModeltoBsonDocument(y)))(Iteratee.foreach[BSONDocument](z => PersistenceStore.saveData(z)))
  }
}
