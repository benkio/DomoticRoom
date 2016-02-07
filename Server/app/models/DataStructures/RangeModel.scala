package models.DataStructures

import org.joda.time.DateTime
import reactivemongo.bson.{BSONObjectID, Macros, BSONDateTime, BSONHandler}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
object RangeModel {

  case class Range(minBound: Double, maxBound: Double, rt: Int)

  case class RangeBoolean(value: Boolean, rt: Int)

  case class RangeBooleanDBJson(id: BSONObjectID, value: Boolean, rangeType: Int, dateCreated: DateTime)

  case class RangeDBJson(id: BSONObjectID, minBound: Double, maxBound: Double, rangeType: Int, dateCreated: DateTime)

  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)

    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val RangeBooleanDBBsonHandler =  Macros.handler[RangeBooleanDBJson]
  implicit val RangeDBBsonHandler = Macros.handler[RangeDBJson]

  object RangeType extends Enumeration {
    type RangeType = Value
    val Gas, Light, Movement, Temperature, Humidity = Value
  }

  object RangeBooleanDBJsonModel {
    val Id = "_id"
    val value = "value"
    val rangeType = "type"
    val dateCreated = "dateCreated"
  }

  object RangeDBJsonModel {
    val Id = "_id"
    val minBound = "minBound"
    val maxBound = "maxBound"
    val rangeType = "type"
    val dateCreated = "dateCreated"
    val RangeDBCollectionName = "Ranges"
  }

}