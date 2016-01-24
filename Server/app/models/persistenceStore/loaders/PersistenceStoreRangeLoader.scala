package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreRangeLoader
import models.{RangeTypeUtil, RangeType}
import org.joda.time.format.{DateTimeFormatterBuilder, DateTimeFormatter}
import org.joda.time.{ReadableDuration,DateTime}

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee.{Enumeratee, Enumerator}


import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONInteger, BSONString, BSONDocument}
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeLoader  @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends IPersistenceStoreRangeLoader
    with ReactiveMongoComponents{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .appendTimeZoneOffset("Z", true, 2, 4)
    .toFormatter


  val rangesCollection : BSONCollection = reactiveMongoApi.db.collection[BSONCollection]("Ranges")

  override def loadRange(rangeType: RangeType, startDate: DateTime, duration:ReadableDuration) = {
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      "type" -> BSONString(RangeTypeUtil.RangeType2Int(rangeType).toString),
      "dateCreation" -> BSONDocument(
        "$gte" -> BSONString(startDateString),
        "$lt" -> BSONString(finalDate)
      )
    )

    rangesCollection.find(query).cursor[BSONDocument]().enumerate()

  }

  override def loadLastRange(rangeType: RangeType) = {
    val query = BSONDocument(
      "type" -> BSONInteger (RangeTypeUtil.RangeType2Int(rangeType))
    )

    val futureResult = rangesCollection.find(query).sort(BSONDocument("dateCreated" -> -1)).one[BSONDocument]
    Enumerator(futureResult) &> Enumeratee.mapM(identity)
  }

  override def loadLastRanges = {

    import rangesCollection.BatchCommands.AggregationFramework.{Group, Max }

    val command = Group(BSONString("$type"))("realmaxid" -> Max("$_id"))

    val findidQuery = rangesCollection.aggregate(command) flatMap {r =>
      Future.sequence(r.documents map { x =>
          val t = x.get("realmaxid").get
          rangesCollection.find(BSONDocument("_id" -> t)).cursor[BSONDocument]() collect[List]()
        }
      )
    } flatMap(x => Future{x.flatten})
    (Enumerator(findidQuery) &>
      Enumeratee.mapM(identity)) &>
      Enumeratee.mapFlatten(x => Enumerator.enumerate(x))
  }
}
