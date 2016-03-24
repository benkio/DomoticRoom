package controllers.StreamBuilder

import interfaces.streamBuilder.IDataLoadStreamBuilder
import models.DataStructures.DataDBJson
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
  * Created by root on 3/23/16.
  */
object LoadDataStreamBuilder extends IDataLoadStreamBuilder{
  override def getDataStream(duration: Duration): Enumerator[JsValue] = {
    val stream = (PersistenceStore.loadCurrentSensorDataContinuously(duration) &>
      Enumeratee.map(x => DataDBJson.validateBsonDocument(x)) &>
      Enumeratee.map(x => DataDBJson.DataJsonModelToJson(x)))
    stream
  }
}