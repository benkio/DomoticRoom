package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import models.DataStructures.DataDBJson
import models.DataStructures.DataDBJson.DataAnalizeDBJson
import models.DataStructures.SensorModel.SensorType.SensorType
import org.joda.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import org.joda.time.{DateTime, ReadableDuration}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.json.{JsNumber, JsObject, JsString}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.{BSONDocument, BSONString}
import models.DataStructures.SensorModel._
import reactivemongo.core.commands.Match

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataLoader(val reactiveMongoApi : ReactiveMongoApi) extends IPersistenceStoreDataLoader{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .appendTimeZoneOffset("Z", true, 2, 4)
    .toFormatter();

  val dataCollection : JSONCollection = reactiveMongoApi.db.collection[JSONCollection](DataDBJson.DataDBCollectionName)

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

    val findidQuery: Future[List[BSONDocument]] = loadCurrentSensorsDataFuture(Duration.Zero)

    (Enumerator(findidQuery) &>
      Enumeratee.mapM(identity)) &>
      Enumeratee.mapFlatten(x => Enumerator.enumerate(x))
  }

  override def loadCurrentSensorDataContinuously(duration : FiniteDuration): Enumerator[BSONDocument] ={

    val findidQuery = loadCurrentSensorsDataFuture(duration);

    (Enumerator.repeatM(findidQuery) &>
      Enumeratee.mapFlatten(x => Enumerator.enumerate(x)))
  }

  override def loadCurrentSensorData(sensorName: String) = {
    val query = BSONDocument(
      DataDBJson.sensorName -> BSONString(sensorName)
    )

    val futureResult = dataCollection.find(query).sort(JsObject(Seq(DataDBJson.dateCreation -> JsNumber(-1)))).one[BSONDocument]

    Enumerator(futureResult) &> Enumeratee.mapM(identity)
  }

  override def loadMininumValue(sensorType: SensorType) = {
    import dataCollection.BatchCommands.AggregationFramework.{Group, Max, Match}

    val filteringCommand = Match(JsObject(Seq(DataDBJson.dataType -> JsNumber(sensorTypeToInt(sensorType)))))
    val groupCommand = Group(JsString("null"))("maxValue" -> Max(DataDBJson.value))


    val average = dataCollection.aggregate(filteringCommand,List(groupCommand)) map (x => x.documents.map(y => {
      DataAnalizeDBJson(sensorTypeToInt(sensorType), y.value("maxValue").as[Double])
    }).head)

    Enumerator(average) &> Enumeratee.mapM(identity)
  }

  override def loadAverageValue(sensorType: SensorType)  = {
    import dataCollection.BatchCommands.AggregationFramework.{Group, Avg, Match}

    val filteringCommand = Match(JsObject(Seq(DataDBJson.dataType -> JsNumber(sensorTypeToInt(sensorType)))))
    val groupCommand = Group(JsString("null"))("avg" -> Avg(DataDBJson.value))

    val average = dataCollection.aggregate(filteringCommand,List(groupCommand)) map (x => x.documents.map(y => {
      DataAnalizeDBJson(sensorTypeToInt(sensorType), y.value("avg").as[Double])
    }).head)

    Enumerator(average) &> Enumeratee.mapM(identity)
  }

  override def loadMaximumValue(sensorType: SensorType) = {
    import dataCollection.BatchCommands.AggregationFramework.{Group, Max, Match}

    val filteringCommand = Match(JsObject(Seq(DataDBJson.dataType -> JsNumber(sensorTypeToInt(sensorType)))))
    val groupCommand = Group(JsString("null"))("maxValue" -> Max(DataDBJson.value))


    val average = dataCollection.aggregate(filteringCommand,List(groupCommand)) map (x => x.documents.map(y => {
      DataAnalizeDBJson(sensorTypeToInt(sensorType), y.value("maxValue").as[Double])
    }).head)

    Enumerator(average) &> Enumeratee.mapM(identity)
  }

  protected def loadCurrentSensorsDataFuture(delay : FiniteDuration): Future[List[BSONDocument]] = {
    import dataCollection.BatchCommands.AggregationFramework.{Group, Max}

    val system = akka.actor.ActorSystem("system")

    val delayFuture = akka.pattern.after(delay, using = system.scheduler)(Future.successful(None))

    val command =
      Group(JsString("$" + DataDBJson.dataType))("realmaxdate" -> Max(DataDBJson.dateCreation))

    val findidQuery = dataCollection.aggregate(command) flatMap { r =>
      Future.sequence(r.documents map { x =>
        val t = x.value("realmaxdate")
        dataCollection.find(BSONDocument(DataDBJson.dateCreation -> t)).cursor[BSONDocument]() collect[List]()
      }
      )
    } flatMap (x => Future {
      Await.result(delayFuture,Duration.Inf)
      x.flatten
    })
    findidQuery
  }

}
