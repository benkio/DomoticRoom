package models.persistenceStore.loaders

import javax.inject.Inject

import interfaces.presistenceStore.IPersistenceStoreRangeLoader
import models.DataStructures.RangeModel
import models.DataStructures.RangeModel.RangeType.RangeType
import models.DataStructures.RangeModel.{RangeDBJsonModel, RangeType}
import org.joda.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import org.joda.time.{DateTime, ReadableDuration}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.libs.json.{JsNumber, JsObject, JsString}
import reactivemongo.play.json._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONInteger, BSONString}

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeLoader  @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends IPersistenceStoreRangeLoader
    with ReactiveMongoComponents{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern(RangeModel.ISO8601)
    .toFormatter


  val rangesCollection : JSONCollection = reactiveMongoApi.db.collection[JSONCollection](RangeDBJsonModel.RangeDBCollectionName)

  override def loadRange(rangeType: RangeType, startDate: DateTime, duration:ReadableDuration) = {
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      RangeDBJsonModel.rangeType -> BSONInteger(RangeModel.rangeTypeToInt(rangeType)),
      RangeDBJsonModel.dateCreated -> BSONDocument(
        "$gte" -> BSONString(startDateString),
        "$lt" -> BSONString(finalDate)
      )
    )

    println(toJSON(query))

    rangesCollection.find(query).cursor[JsObject].enumerate()

  }

  override def loadLastRange(rangeType: RangeType.Value) = {
    val query = BSONDocument(
      RangeDBJsonModel.rangeType -> BSONInteger(RangeModel.rangeTypeToInt(rangeType))
    )

    rangesCollection.find(query).sort(JsObject(Seq(RangeDBJsonModel.dateCreated -> JsNumber(-1)))).one[JsObject]
  }

  override def loadLastRanges = {
    import rangesCollection.BatchCommands.AggregationFramework.{Group, Max}

    val command = Group(JsString("$"+RangeDBJsonModel.rangeType))("realmaxdate" -> Max(RangeDBJsonModel.dateCreated))

    val findidQuery = rangesCollection.aggregate(command) flatMap {r =>
      Future.sequence(r.documents map { x =>
          val t = x.value("realmaxdate")
          rangesCollection.find(BSONDocument(RangeDBJsonModel.dateCreated -> t)).cursor[BSONDocument]() collect[List]()
        }
      )
    } flatMap(x => Future{x.flatten})
    (Enumerator(findidQuery) &>
      Enumeratee.mapM(identity)) &>
      Enumeratee.mapFlatten(x => Enumerator.enumerate(x))
  }
}