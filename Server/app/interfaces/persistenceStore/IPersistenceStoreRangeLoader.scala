package interfaces.presistenceStore

import models.DataStructures.RangeModel.RangeType
import org.joda.time.{DateTime, ReadableDuration}
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsObject
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreRangeLoader {
  def loadRange(rangeType: RangeType.Value, startDate: DateTime, duration: ReadableDuration) :  Enumerator[JsObject]
  def loadLastRanges : Enumerator[BSONDocument]
  def loadLastRange(rangeType: RangeType.Value) : Future[Option[JsObject]]
}
