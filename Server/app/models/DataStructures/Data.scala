package models.DataStructures

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, JsPath}


/**
  * Created by parallels on 1/26/16.
  */
object DataDBJson {
  //Fields
  val id = "_id"
  val dateCreation = "dateCreation"
  val rangeViolation = "rangeViolation"
  val rangeViolationDelta = "delta"
  val sensorName = "sensorName"
  val dataType = "type"
  val value = "value"

  case class rangeViolationDBJson(delta : Double)
  case class DataDBJsonModel(id : String,
                             dateCreation: DateTime,
                             rangeViolation : Option[rangeViolationDBJson],
                             sensorName : String,
                             dataType : Double,
                             value : Double)

  implicit val rangeViolationDBJsonReader : Reads[rangeViolationDBJson] =
    (JsPath \ rangeViolationDelta).read[Double].map(rangeViolationDBJson)

  implicit val dataDBJsonModelViolation: Reads[DataDBJsonModel] = (
    (JsPath \ id).read[String] and
      (JsPath \ dateCreation).read[DateTime] and
      (JsPath \ rangeViolation).readNullable[rangeViolationDBJson] and
      (JsPath \ sensorName).read[String] and
      (JsPath \ dataType).read[Double] and
      (JsPath \ value).read[Double])(DataDBJsonModel.apply _)

  /*def validateJsonData(Json: JsValue) = {
    val result = Json.validate[dataDBJsonModelViolation]
    val result2 = Json.validate[DataDBJsonModel]

    (result,result2) match {
      case JsResult.
    }
  }*/


}

object DataDBCollection{
  val name = "Data"
}