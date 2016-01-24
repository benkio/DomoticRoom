package interfaces.presistenceStore

import models.RangeType
import org.joda.time.{ReadableDuration, DateTime}
import play.api.libs.iteratee.Enumerator
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreRangeLoader {
  def loadRange(rangeType: RangeType, startDate: DateTime, duration: ReadableDuration) : Enumerator[BSONDocument]
  def loadLastRanges : Enumerator[BSONDocument]
  def loadLastRange(rangeType: RangeType) : Enumerator[Option[BSONDocument]]
}
