package models.DataStructures

import play.api.libs.functional.syntax._
import play.api.libs.json._
import reactivemongo.bson._

/**
  * Created by parallels on 1/26/16.
  */

object DataDBJson {
  import org.joda.time.DateTime

  //Fields
  val id = "_id"
  val dateCreation = "dateCreation"
  val rangeViolation = "rangeViolation"
  val rangeViolationDelta = "delta"
  val sensorName = "sensorName"
  val dataType = "type"
  val value = "value"
  val DataDBCollectionName = "Data"

  case class RangeViolationDBJson(delta : Double)
  case class DataDBJsonModel(id : String,
                             dateCreation: DateTime,
                             rangeViolation : Option[RangeViolationDBJson],
                             sensorName : String,
                             dataType : Double,
                             value : Double)


  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val RangeViolationDBBsonHandler =  Macros.handler[RangeViolationDBJson]
  implicit val DataDBBsonHandler = Macros.handler[DataDBJsonModel]

  implicit val rangeViolationDBJsonReader : Reads[RangeViolationDBJson] =
    (JsPath \ rangeViolationDelta).read[Double].map(RangeViolationDBJson)

  implicit val dataDBJsonModelViolationReader: Reads[DataDBJsonModel] = (
    (JsPath \ id).read[String] and
      (JsPath \ dateCreation).read[DateTime] and
      (JsPath \ rangeViolation).readNullable[RangeViolationDBJson] and
      (JsPath \ sensorName).read[String] and
      (JsPath \ dataType).read[Double] and
      (JsPath \ value).read[Double])(DataDBJsonModel.apply _)

  implicit val rangeViolationDBJsonWrites = new Writes[Option[RangeViolationDBJson]] {
    def writes(data: Option[RangeViolationDBJson]) = data match {
      case Some(d) => Json.obj(
        rangeViolationDelta -> d.delta
      )
      case None => JsString("")
    }
  }

  implicit val dataDBJsonModelViolationWrites = new Writes[DataDBJsonModel] {
    def writes(data: DataDBJsonModel) = Json.obj(
      id -> BSONObjectID.generate.toString(),
      dateCreation -> data.dateCreation,
      rangeViolation -> Json.toJson[Option[RangeViolationDBJson]](data.rangeViolation),
      sensorName -> data.sensorName,
      dataType -> data.dataType,
      value -> data.value
    )
  }

  def validateJsonData(Json: JsValue) = Json.validate[DataDBJsonModel].isSuccess
  def getJsonDataValidated(JsonValidated: JsSuccess[DataDBJsonModel]) = JsonValidated.value
  def toBsonDocument(t: DataDBJsonModel) = DataDBBsonHandler.write(t)
  def fromBsonDocument(x: BSONDocument) = DataDBBsonHandler.read(x)
}