package interfaces.presistenceStore

import org.joda.time.{ReadableDuration, DateTime, Interval}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreDataLoader {
  def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration) : Future[List[BSONDocument]]
  def loadCurrentSensorData(sensorName : String) : Future[Option[BSONDocument]]
  def loadCurrentSensorsData() : Future[List[BSONDocument]]
}
