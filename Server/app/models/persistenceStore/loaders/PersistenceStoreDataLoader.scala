package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import org.joda.time.format.{DateTimeFormatterBuilder, DateTimeFormatter}
import org.joda.time.{ReadableDuration, DateTime}
import play.modules.reactivemongo.json._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.modules.reactivemongo.ReactiveMongoApi

import reactivemongo.bson.{ BSONDocument, BSONString}

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

    return dataCollection.find(query).cursor[BSONDocument].collect[List]();
  }

  /*
 *  db.runCommand({group:{ns: 'Data',key: { type: 1},$reduce: function (obj,prev) { prev._id = isNaN(prev._id) ? obj._id : Math.max(prev._id, obj._id);  },initial:{}}})
 *  db.Data.aggregate({$group: {_id: "$type", realmaxid: {$max: "$_id"}}})
 *  map result with other values
 */
  override def loadCurrentSensorsData(): Unit = {

    //TODO: find how to import the Aggregation Framework

    import dataCollection.BatchCommands.AggregationFramework.{
    AggregationResult, Ascending, First, Group, Last, Project, Sort, SumField,Max
    }

    val command = Group(BSONString("$type"))("realmaxid" -> Max("$_id"))
   // dataCollection.aggregate(command)
  }

  override def loadCurrentSensorData(sensorName: String): Unit = {
    val query = BSONDocument(
      "sensorName" -> BSONString(sensorName)
    )

    return dataCollection.find(query).cursor[BSONDocument].collect[List]();
  }
}
