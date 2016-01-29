package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreRangeLoader
import models.DataStructures.RangeModel.{RangeType, RangeDBJsonModel}
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


  val rangesCollection : BSONCollection = reactiveMongoApi.db.collection[BSONCollection](RangeDBJsonModel.RangeDBCollectionName)

  override def loadRange(rangeType: RangeType.Value, startDate: DateTime, duration:ReadableDuration) = {
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      RangeDBJsonModel.rangeType -> BSONInteger(rangeType.id),
      RangeDBJsonModel.dateCreated -> BSONDocument(
        "$gte" -> BSONString(startDateString),
        "$lt" -> BSONString(finalDate)
      )
    )

    rangesCollection.find(query).cursor[BSONDocument]().enumerate()

  }

  override def loadLastRange(rangeType: RangeType.Value) = {
    val query = BSONDocument(
      RangeDBJsonModel.rangeType -> BSONInteger(rangeType.id)
    )

    val futureResult = rangesCollection.find(query).sort(BSONDocument(RangeDBJsonModel.dateCreated -> -1)).one[BSONDocument]
    Enumerator(futureResult) &> Enumeratee.mapM(identity)
  }

  override def loadLastRanges = {

    import rangesCollection.BatchCommands.AggregationFramework.{Group, Max }

    val command = Group(BSONString("$"+RangeDBJsonModel.rangeType))("realmaxid" -> Max("$"+RangeDBJsonModel.Id))

    val findidQuery = rangesCollection.aggregate(command) flatMap {r =>
      Future.sequence(r.documents map { x =>
          val t = x.get("realmaxid").get
          rangesCollection.find(BSONDocument(RangeDBJsonModel.Id -> t)).cursor[BSONDocument]() collect[List]()
        }
      )
    } flatMap(x => Future{x.flatten})
    (Enumerator(findidQuery) &>
      Enumeratee.mapM(identity)) &>
      Enumeratee.mapFlatten(x => Enumerator.enumerate(x))
  }
}