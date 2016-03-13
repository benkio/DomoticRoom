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

  //Fields
  val dateCreation = "date"
  val sensorName = "sensorName"
  val dataType = "type"
  val value = "value"

  case class DataReceivedJsonModel( sensorName : String,
                                    dateCreation: DateTime,
                                    value : Double,                         
                                    dataType : Double)
  
  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val DataReceivedJsonModelReader: Reads[DataReceivedJsonModel] = (
    (JsPath \ sensorName).read[String] and  
      (JsPath \ dateCreation).read[DateTime] and 
      (JsPath \ value).read[Double] and  
      (JsPath \ dataType).read[Double])(DataReceivedJsonModel.apply _)

  implicit val DataReceivedJsonModelWrites = new Writes[DataReceivedJsonModel] {
    def writes(data: DataReceivedJsonModel) = Json.obj(
      sensorName -> data.sensorName,
      dateCreation -> data.dateCreation,
      value -> data.value,
      dataType -> data.dataType
    )
  }

  def readJsValueToReceivedDataJsonModel(data: JsValue) = data.validate[DataReceivedJsonModel](DataReceivedJsonModelReader)
  def validateJsValueToReceivedDataJsonModel(data: JsValue) = readJsValueToReceivedDataJsonModel(data: JsValue).isSuccess
}