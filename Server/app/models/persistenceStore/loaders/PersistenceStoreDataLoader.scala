package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import org.joda.time.format.{DateTimeFormatterBuilder, DateTimeFormatter}
import org.joda.time.{ReadableDuration, DateTime}
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.json._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.{ BSONString, BSONDocument}

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataLoader(val reactiveMongoApi : ReactiveMongoApi) extends IPersistenceStoreDataLoader{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .appendTimeZoneOffset("Z", true, 2, 4)
    .toFormatter();

  override def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration): Future[List[BSONDocument]] = {
    val dataCollection : JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("Data")
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      "sensorName" -> BSONString(sensorName),
      "dateCreation" -> BSONDocument(
        "$gte: " -> BSONString(startDateString),
        "$lt: " -> BSONString(finalDate)
      )
    )

    return dataCollection.find(query).cursor[BSONDocument].collect[List]();
  }

  override def loadCurrentSensorsData(): Unit = ??? //TODO

  override def loadCurrentSensorData(sensorName: String): Unit = ??? //TODO
}
