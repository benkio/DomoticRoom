package models.DataStructures

import models.DataStructures.RangeModel.RangeType.RangeType
import models.DataStructures.SensorModel.SensorType
import models.DataStructures.SensorModel.SensorType.SensorType
import org.joda.time.DateTime
import reactivemongo.bson.{BSONObjectID, Macros, BSONDateTime, BSONHandler}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
object RangeModel {

  // MODELS

  trait IRange

  case class Range(minBound: Double, maxBound: Double, rt: Int) extends IRange
  case class RangeBoolean(value: Boolean, rt: Int) extends IRange
  case class RangeBooleanDBJson(id: BSONObjectID, value: Boolean, rangeType: Int, dateCreated: DateTime)
  case class RangeDBJson(id: BSONObjectID, minBound: Double, maxBound: Double, rangeType: Int, dateCreated: DateTime)

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

  // CONVERSIONS

  // BSON DOCUMENT

  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)

    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val RangeBooleanDBBsonHandler =  Macros.handler[RangeBooleanDBJson]
  implicit val RangeDBBsonHandler = Macros.handler[RangeDBJson]

  // SENSOR TYPE

  def rangeTypeToSensorType(rangeType : RangeType): SensorType = rangeType match {
    case RangeType.Gas => SensorType.Gas
    case RangeType.Humidity => SensorType.Humidity
    case RangeType.Light => SensorType.Light
    case RangeType.Movement => SensorType.Movement
    case RangeType.Temperature => SensorType.Temperature
  }

  // INTEGER

  def intToRangeType(id : Int): RangeType = id match {
    case 1 => RangeType.Gas
    case 2 => RangeType.Humidity
    case 3 => RangeType.Light
    case 4 => RangeType.Movement
    case 5 => RangeType.Temperature
  }
}