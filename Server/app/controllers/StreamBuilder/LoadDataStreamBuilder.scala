package controllers.StreamBuilder

import interfaces.streamBuilder.IDataLoadStreamBuilder
import models.DataStructures.DataDBJson
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by root on 3/23/16.
  */
object LoadDataStreamBuilder extends IDataLoadStreamBuilder{
  override def getDataStream(duration: FiniteDuration): Enumerator[JsValue] = {
    val stream = (PersistenceStore.loadCurrentSensorDataContinuously(duration) &>
      Enumeratee.map(x => DataDBJson.validateBsonDocument(x)) &>
      Enumeratee.map(x => DataDBJson.DataJsonModelToJson(x)))
    stream
  }

  def getDataStreamSingle() : Enumerator[JsValue] = {
    val stream = (PersistenceStore.loadCurrentSensorsData() &>
      Enumeratee.map(x => DataDBJson.validateBsonDocument(x)) &>
      Enumeratee.map(x => DataDBJson.DataJsonModelToJson(x)))
    stream
  }

  def getDataMininum : Enumerator[JsValue] = { ??? }

  def getDataMaxinum : Enumerator[JsValue] = { ??? }

  def getDataAverage : Enumerator[JsValue] = { ??? }
}