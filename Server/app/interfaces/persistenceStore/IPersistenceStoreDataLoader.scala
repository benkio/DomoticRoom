package interfaces.presistenceStore

import models.DataStructures.DataDBJson.DataAnalizeDBJson
import models.DataStructures.SensorModel.SensorType.SensorType
import org.joda.time.{DateTime, Interval, ReadableDuration}
import play.api.libs.iteratee.Enumerator
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future
import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreDataLoader {
  def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration) : Enumerator[BSONDocument]
  def loadCurrentSensorData(sensorName : String) : Enumerator[Option[BSONDocument]]
  def loadCurrentSensorsData() : Enumerator[BSONDocument]
  def loadCurrentSensorDataContinuously(duration : FiniteDuration) : Enumerator[BSONDocument]
  def loadMininumValue(sensorType : SensorType) : Enumerator[Option[DataAnalizeDBJson]]
  def loadMaximumValue(sensorType : SensorType) : Enumerator[Option[DataAnalizeDBJson]]
  def loadAverageValue(sensorType : SensorType) : Enumerator[Option[DataAnalizeDBJson]]
}
