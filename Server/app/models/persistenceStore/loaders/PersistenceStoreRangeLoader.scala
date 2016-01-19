package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreRangeLoader
import models.{RangeTypeUtil, RangeType, SensorType}
import org.joda.time.format.{DateTimeFormatterBuilder, DateTimeFormatter}
import org.joda.time.{ReadableDuration,DateTime}

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONString, BSONDocument}
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeLoader  @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStoreRangeLoader with ReactiveMongoComponents{

  val patternFormat:DateTimeFormatter = new DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .appendTimeZoneOffset("Z", true, 2, 4)
    .toFormatter();


  val rangesCollection : BSONCollection = reactiveMongoApi.db.collection[BSONCollection]("Ranges")

  override def loadRange(rangeType: RangeType, startDate: DateTime, duration:ReadableDuration): Unit = {
    val finalDate = startDate.plus(duration).toString(patternFormat)
    val startDateString = startDate.toString(patternFormat)

    val query = BSONDocument(
      "type" -> BSONString(RangeTypeUtil.RangeType2Int(rangeType).toString),
      "dateCreation" -> BSONDocument(
        "$gte" -> BSONString(startDateString),
        "$lt" -> BSONString(finalDate)
      )
    )

    return rangesCollection.find(query).cursor[BSONDocument].collect[List]();

  }

  override def loadLastRange(sensorType: SensorType): Unit = ??? //TODO

  override def loadLastRanges: Unit = ??? //TODO
}
