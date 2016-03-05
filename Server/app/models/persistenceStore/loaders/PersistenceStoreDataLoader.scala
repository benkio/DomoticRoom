package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import models.DataStructures.DataDBJson
import org.joda.time.format.{DateTimeFormatterBuilder, DateTimeFormatter}
import org.joda.time.{ReadableDuration, DateTime}
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.modules.reactivemongo.json._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.{BSONDocument, BSONString}
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataLoader(val reactiveMongoApi : ReactiveMongoApi) extends IPersistenceStoreDataLoader{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .appendTimeZoneOffset("Z", true, 2, 4)
    .toFormatter();

  val dataCollection : BSONCollection = reactiveMongoApi.db.collection[BSONCollection](DataDBJson.DataDBCollectionName)

  override def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration) = {
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      DataDBJson.sensorName -> BSONString(sensorName),
      DataDBJson.dateCreation -> BSONDocument(
        "$gte" -> BSONString(startDateString),
        "$lt" -> BSONString(finalDate)
      )
    )

    dataCollection.find(query).cursor[BSONDocument]().enumerate()
  }

  override def loadCurrentSensorsData() : Enumerator[BSONDocument] = {

    import dataCollection.BatchCommands.AggregationFramework.{Group, Max }

    val command =
      Group(BSONString("$" + DataDBJson.dataType))("realmaxid" -> Max("$" + DataDBJson.id))

    val findidQuery = dataCollection.aggregate(command) flatMap {r =>
      Future.sequence(r.documents map { x =>
        val t = x.get("realmaxid").get
        dataCollection.find(BSONDocument(DataDBJson.id -> t)).cursor[BSONDocument]() collect[List]()
        }
      )
    } flatMap(x => Future{x.flatten})

    (Enumerator(findidQuery) &>
      Enumeratee.mapM(identity)) &>
      Enumeratee.mapFlatten(x => Enumerator.enumerate(x))
  }

  override def loadCurrentSensorData(sensorName: String) = {
    val query = BSONDocument(
      DataDBJson.sensorName -> BSONString(sensorName)
    )

    val futureResult = dataCollection.find(query).sort(BSONDocument(DataDBJson.dateCreation -> -1)).one[BSONDocument]

    Enumerator(futureResult) &> Enumeratee.mapM(identity)
  }
}