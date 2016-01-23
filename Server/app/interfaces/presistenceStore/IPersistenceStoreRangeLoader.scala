package interfaces.presistenceStore

import models.RangeType
import org.joda.time.{ReadableDuration, DateTime}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreRangeLoader {
  def loadRange(rangeType: RangeType, startDate: DateTime, duration: ReadableDuration) : Future[List[BSONDocument]]
  def loadLastRanges : Future[List[BSONDocument]]
  def loadLastRange(rangeType: RangeType) : Future[Option[BSONDocument]]
}
