package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import org.joda.time.format.{DateTimeFormatterBuilder, DateTimeFormatter}
import org.joda.time.{ReadableDuration, DateTime}
import play.modules.reactivemongo.json._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.modules.reactivemongo.ReactiveMongoApi

import reactivemongo.bson.{BSONDocument, BSONString}

import reactivemongo.api.collections.bson.{ BSONCollection}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataLoader(val reactiveMongoApi : ReactiveMongoApi) extends IPersistenceStoreDataLoader{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .appendTimeZoneOffset("Z", true, 2, 4)
    .toFormatter();

  val dataCollection : BSONCollection = reactiveMongoApi.db.collection[BSONCollection]("Data")

  override def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration): Future[List[BSONDocument]] = {
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      "sensorName" -> BSONString(sensorName),
      "dateCreation" -> BSONDocument(
        "$gte" -> BSONString(startDateString),
        "$lt" -> BSONString(finalDate)
      )
    )

    return dataCollection.find(query).cursor[BSONDocument]().collect[List]()
  }

  override def loadCurrentSensorsData() : Future[List[BSONDocument]] = {

    import dataCollection.BatchCommands.AggregationFramework.{Group, Max }

    val command = Group(BSONString("$type"))("realmaxid" -> Max("$_id"))

    val findidQuery = dataCollection.aggregate(command) flatMap {r =>
      Future.sequence(r.documents map { x =>
        val t = x.get("realmaxid").get
        dataCollection.find(BSONDocument("_id" -> t)).cursor[BSONDocument]() collect[List]()
        }
      )
    } flatMap(x => Future{x.flatten})
    findidQuery
  }

  override def loadCurrentSensorData(sensorName: String) = {
    val query = BSONDocument(
      "sensorName" -> BSONString(sensorName)
    )

    dataCollection.find(query).sort(BSONDocument("dateCreated" -> -1)).one[BSONDocument]
  }
}
