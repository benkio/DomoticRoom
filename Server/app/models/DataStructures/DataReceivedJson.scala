package models.DataStructures

import play.api.libs.functional.syntax._
import play.api.libs.json._
import reactivemongo.bson._

/**
  * Created by parallels on 1/26/16.
  */
/*
* [
  {
    "sensorName": "nomeSensore",
    "sensorType": 1,
    "value": true,
    "date": "2012-04-23T18:25:43.511Z"
  },
  {
    "sensorName": "nomeSensore",
    "sensorType": 2,
    "value": 100,
    "date": "2013-04-23T18:25:43.511Z"
  },
  {
    "sensorName": "nomeSensore",
    "sensorType": 2,
    "value": 100,
    "date": "2013-04-23T18:25:43.511Z"
  }
]
* */

object DataReceivedJson {
  import org.joda.time.DateTime

  // Fields
  val dateCreation = "date"
  val sensorName = "sensorName"
  val dataType = "type"
  val value = "value"

  // Model
  case class DataReceivedJsonModel[T]( sensorName : String,
                                    dateCreation: DateTime,
                                    value : T,
                                    dataType : Double)

  // Converters
  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit def DataReceivedJsonModelReader[T](implicit fmt: Reads[T]): Reads[DataReceivedJsonModel[T]] = new Reads[DataReceivedJsonModel[T]] {
    def reads(json: JsValue): JsResult[DataReceivedJsonModel[T]] = JsSuccess(new DataReceivedJsonModel[T] (
      (json \ sensorName).as[String],
      (json \ dateCreation).as[DateTime],
      (json \ value).as[T],
      (json \ dataType).as[Double]
    ))
  }

  implicit def DataReceivedJsonModelWriter[T](implicit fmt: Writes[T]): Writes[DataReceivedJsonModel[T]] = new Writes[DataReceivedJsonModel[T]] {
    def writes(ts: DataReceivedJsonModel[T]) = JsObject(Seq(
      sensorName -> JsString(ts.sensorName),
      dateCreation -> JsString(ts.dateCreation.toString()),
      value -> Json.toJson[T](ts.value),
      dataType -> JsNumber(ts.dataType)
    ))
  }
}