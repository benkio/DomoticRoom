package models.DataStructures

import models.DataStructures.RangeModel.RangeType.RangeType
import models.DataStructures.SensorModel.SensorType
import models.DataStructures.SensorModel.SensorType.SensorType
import org.joda.time.DateTime
import reactivemongo.bson._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import reactivemongo.play.json._

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
object RangeModel {
  val ISO8601 = "yyyy-MM-dd HH:mm:ss.SSS"

  // MODELS

  trait IRange
  trait IDBRange

  case class Range(minBound: Double, maxBound: Double, rt: Int) extends IRange
  case class RangeBoolean(value: Boolean, rt: Int) extends IRange
  case class RangeBooleanDBJson(_id: BSONObjectID, value: Boolean, rangeType: Int, dateCreated: DateTime) extends IDBRange
  case class RangeDBJson(_id: BSONObjectID, minBound: Double, maxBound: Double, rangeType: Int, dateCreated: DateTime) extends IDBRange

  object RangeType extends Enumeration {
    type RangeType = Value
    val Gas, Light, Movement, Temperature, Humidity = Value
  }

  object RangeBooleanDBJsonModel {
    val Id          = "_id"
    val value       = "value"
    val rangeType   = "rangeType"
    val dateCreated = "dateCreated"
  }

  object RangeDBJsonModel {
    val Id                    = "_id"
    val minBound              = "minBound"
    val maxBound              = "maxBound"
    val rangeType             = "rangeType"
    val dateCreated           = "dateCreated"
    val RangeDBCollectionName = "Ranges"
  }

  //////////////////////////////////// 
  // CONVERSIONS
  //////////////////////////////////// 
  // BSON DOCUMENT
  ////////////////////////////////////

  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)

    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val RangeBooleanDBBsonHandler =  Macros.handler[RangeBooleanDBJson]
  implicit val RangeDBBsonHandler = Macros.handler[RangeDBJson]

  def convertToRangeDBJson(bson : BSONDocument) = bson.as[RangeDBJson]
  def convertToRangeBooleanDBJson(bson : BSONDocument) = bson.as[RangeBooleanDBJson]

  ////////////////////////////////////
  // JSON DOCUMENT
  ////////////////////////////////////

  implicit val dateReads = Reads.jodaDateReads(ISO8601)

  implicit def DataDoubleReceivedJsonModelReader : Reads[RangeBooleanDBJson] = (
    (JsPath \ RangeBooleanDBJsonModel.Id).read[BSONObjectID]         and
    (JsPath \ RangeBooleanDBJsonModel.value).read[Boolean]           and
      (JsPath \ RangeBooleanDBJsonModel.rangeType).read[Int]         and
      (JsPath \ RangeBooleanDBJsonModel.dateCreated).read[DateTime]
    )(RangeBooleanDBJson.apply _)

  implicit def DataBooleanReceivedJsonModelReader : Reads[RangeDBJson] = (
    (JsPath \ RangeDBJsonModel.Id).read[BSONObjectID]           and
      (JsPath \ RangeDBJsonModel.minBound).read[Double]         and
      (JsPath \ RangeDBJsonModel.maxBound).read[Double]         and
      (JsPath \ RangeDBJsonModel.rangeType).read[Int]           and
      (JsPath \ RangeDBJsonModel.dateCreated).read[DateTime]
    )(RangeDBJson.apply _)

  //////////////////////////////////// 
  // RANGE TYPE TO - FROM
  ////////////////////////////////////
  // RangeType
  ////////////////////////////////////

  def rangeTypeToSensorType(rangeType : RangeType): SensorType = rangeType match {
    case RangeType.Gas         => SensorType.Gas
    case RangeType.Humidity    => SensorType.Humidity
    case RangeType.Light       => SensorType.Light
    case RangeType.Movement    => SensorType.Movement
    case RangeType.Temperature => SensorType.Temperature
  }

  //////////////////////////////////// 
  // INTEGER
  ////////////////////////////////////

  def intToRangeType(id : Int): RangeType = id match {
    case 1 => RangeType.Gas
    case 2 => RangeType.Humidity
    case 3 => RangeType.Light
    case 4 => RangeType.Movement
    case 5 => RangeType.Temperature
  }

  def rangeTypeToInt(rangeType: RangeType) = rangeType match {
    case RangeType.Gas          => 1
    case RangeType.Humidity     => 2
    case RangeType.Light        => 3
    case RangeType.Movement     => 4
    case RangeType.Temperature  => 5
  }

  ////////////////////////////////////
  // Utilities funcions
  ////////////////////////////////////

  def isBoolean(rangeType : RangeType) = rangeType != RangeType.Temperature && rangeType != RangeType.Humidity

  def getNonBooleanRangeType = RangeType.values.filter(x => !isBoolean(x))
   def getBooleanRangeType = RangeType.values.filter(isBoolean _)

  ////////////////////////////////////
  // Data JSON validation
  //////////////////////////////////// 

  def validateRangeDBJson(data : JsValue) = data.validate[RangeDBJson]
  def validateRangeBooleanDBJson(data : JsValue) = data.validate[RangeBooleanDBJson]

}
