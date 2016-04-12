package interfaces.streamBuilder

import models.DataStructures.DataDBJson.DataAnalizeDBJson
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue

import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by root on 3/23/16.
  */
trait IDataLoadStreamBuilder {
  def getDataStream(duration : FiniteDuration) : Enumerator[JsValue]
  def getDataMininum : Enumerator[DataAnalizeDBJson]

  def getDataMaxinum : Enumerator[DataAnalizeDBJson]

  def getDataAverage : Enumerator[DataAnalizeDBJson]
}
