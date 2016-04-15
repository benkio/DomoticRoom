package controllers.StreamBuilder

import interfaces.streamBuilder.IDataLoadStreamBuilder
import models.DataStructures.{DataDBJson, SensorModel}
import models.DataStructures.DataDBJson.{DataAnalizeDBJson, DataDBJsonModel}
import models.DataStructures.SensorModel.SensorType.SensorType
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

  def getDataMininum : Enumerator[DataAnalizeDBJson] = {
    mapReduceTemplate(PersistenceStore.loadMininumValue)
  }

  def getDataMaxinum : Enumerator[DataAnalizeDBJson] = {
    mapReduceTemplate(PersistenceStore.loadMaximumValue)
  }

  def getDataAverage : Enumerator[DataAnalizeDBJson] = {
    mapReduceTemplate(PersistenceStore.loadAverageValue)
  }

  private def mapReduceTemplate(f: SensorType => Enumerator[Option[DataAnalizeDBJson]]) = {
    val stream : Enumerator[DataAnalizeDBJson] = SensorModel.getBooleanSensorType map { sensorType =>
      f(sensorType).map[DataAnalizeDBJson](x => if (x.isDefined) x.get else DataAnalizeDBJson(-1,0,0))
    } reduce { (x,y) => x.andThen(y)}
    stream
  }
}
