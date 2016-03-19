package models.DataStructures

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by parallels on 1/26/16.
  */
/*
* [
  {
    "sensorName": "nomeSensore",
    "sensorType": 1,
    "value": true,
    "date": "2012-04-23 18:25:43.511"
  },
  {
    "sensorName": "nomeSensore",
    "sensorType": 2,
    "value": 100,
    "date": "2013-04-23 18:25:43.511"
  },
  {
    "sensorName": "nomeSensore",
    "sensorType": 2,
    "value": 100,
    "date": "2013-04-23 18:25:43.511 "
  }
]
* */

object DataReceivedJson {
  private val ISO8601 = "yyyy-MM-dd HH:mm:ss.SSS"

  // Fields
  val dateCreation = "date"
  val sensorName = "sensorName"
  val sensorType = "sensorType"
  val value = "value"

  // Model
  case class DataReceivedDoubleJsonModel( sensorName : String,
                                          sensorType : Double,
                                          value : Double,
                                          dateCreation: DateTime)

  case class DataReceivedBooleanJsonModel(  sensorName : String,
                                            sensorType : Double,
                                            value : Boolean,
                                            dateCreation: DateTime)


  // Converters
  implicit val dateReads = Reads.jodaDateReads(ISO8601)

  implicit def DataDoubleReceivedJsonModelReader : Reads[DataReceivedDoubleJsonModel] = (
      (JsPath \ sensorName).read[String]        and
        (JsPath \ sensorType).read[Double]      and
        (JsPath \ value).read[Double]           and
      (JsPath \ dateCreation).read[DateTime]
    )(DataReceivedDoubleJsonModel.apply _)

  implicit def DataBooleanReceivedJsonModelReader : Reads[DataReceivedBooleanJsonModel] = (
    (JsPath \ sensorName).read[String]          and
      (JsPath \ sensorType).read[Double]        and
      (JsPath \ value).read[Boolean]            and
      (JsPath \ dateCreation).read[DateTime]
    )(DataReceivedBooleanJsonModel.apply _)

  def validateReceivedDataDoubleJson(data : JsValue) = data.validate[DataReceivedDoubleJsonModel]
  def validateReceivedDataBooleanJson(data : JsValue) = data.validate[DataReceivedBooleanJsonModel]
}