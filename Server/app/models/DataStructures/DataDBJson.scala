package models.DataStructures

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

  case class DataRangeViolationDBJson(delta : Double)
  case class DataDBJsonModel[T](id : String,
                             dateCreation: DateTime,
                             rangeViolation : Option[DataRangeViolationDBJson],
                             sensorName : String,
                             dataType : Double,
                             value : T)


  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val RangeViolationDBBsonHandler =  Macros.handler[DataRangeViolationDBJson]

  implicit object DataDBBsonDoubleHandler extends BSONDocumentReader[DataDBJsonModel[Double]] with BSONDocumentWriter[DataDBJsonModel[Double]] {
    def read(bson: BSONDocument): DataDBJsonModel[Double] = DataDBJsonModel[Double](
      bson.getAs[String](id).get,
      bson.getAs[DateTime](dateCreation).get,
      bson.getAs[DataRangeViolationDBJson](rangeViolation),
      bson.getAs[String](sensorName).get,
      bson.getAs[Double](dataType).get,
      bson.getAs[Double](value).get
    )

    override def write(t: DataDBJsonModel[Double]): BSONDocument = BSONDocument(
      id -> t.id,
      dateCreation -> t.dateCreation,
      rangeViolation -> t.rangeViolation,
      sensorName -> t.sensorName,
      dataType -> t.dataType,
      value -> t.value
    )
  }

  implicit object DataDBBsonBooleanHandler extends BSONDocumentReader[DataDBJsonModel[Boolean]] with BSONDocumentWriter[DataDBJsonModel[Boolean]] {
    def read(bson: BSONDocument): DataDBJsonModel[Boolean] = DataDBJsonModel[Boolean](
      bson.getAs[String](id).get,
      bson.getAs[DateTime](dateCreation).get,
      bson.getAs[DataRangeViolationDBJson](rangeViolation),
      bson.getAs[String](sensorName).get,
      bson.getAs[Double](dataType).get,
      bson.getAs[Boolean](value).get
    )

    override def write(t: DataDBJsonModel[Boolean]): BSONDocument = BSONDocument(
      id -> t.id,
      dateCreation -> t.dateCreation,
      rangeViolation -> t.rangeViolation,
      sensorName -> t.sensorName,
      dataType -> t.dataType,
      value -> t.value
    )
  }

  implicit val rangeViolationDBJsonReader : Reads[DataRangeViolationDBJson] =
    (JsPath \ rangeViolationDelta).read[Double].map(DataRangeViolationDBJson)

  implicit def DataDBJsonModelViolationReader[T](implicit fmt: Reads[T]): Reads[DataDBJsonModel[T]] = new Reads[DataDBJsonModel[T]] {
    def reads(json: JsValue): JsResult[DataDBJsonModel[T]] = JsSuccess(new DataDBJsonModel[T] (
      (json \ id).as[String],
      (json \ dateCreation).as[DateTime],
      (json \ rangeViolation).asOpt[DataRangeViolationDBJson],
      (json \ sensorName).as[String],
      (json \ dataType).as[Double],
      (json \ value).as[T]
    )
    )
  }

    implicit def DataReceivedJsonModelWriter[T](implicit fmt: Writes[T]): Writes[DataDBJsonModel[T]] = new Writes[DataDBJsonModel[T]] {
      def writes(ts: DataDBJsonModel[T]) = JsObject(Seq(
      id -> JsString(BSONObjectID.generate.toString()),
      dateCreation -> JsString(ts.dateCreation.toString()),
      rangeViolation -> Json.toJson[Option[DataRangeViolationDBJson]](ts.rangeViolation),
      sensorName -> JsString(ts.sensorName),
      dataType -> JsNumber(ts.dataType),
      value -> Json.toJson[T](ts.value)
    ))
  }

  implicit val rangeViolationDBJsonWrites = new Writes[Option[DataRangeViolationDBJson]] {
    def writes(data: Option[DataRangeViolationDBJson]) = data match {
      case Some(d) => Json.obj(
        rangeViolationDelta -> d.delta
      )
      case None => JsString("")
    }
  }

  def validateJsonData(Json: JsValue) = {
    (Json.validate[DataDBJsonModel[Double]].isSuccess, Json.validate[DataDBJsonModel[Boolean]].isSuccess) match {
      case (true, _)  => Json.validate[DataDBJsonModel[Double]].get
      case (_, true)  => Json.validate[DataDBJsonModel[Boolean]].get
      case _          => throw new Exception
    }

  }
  def DoubleDataJsonModeltoBsonDocument(t: DataDBJsonModel[Double]) = DataDBBsonDoubleHandler.write(t)
  def BooleanDataJsonModeltoBsonDocument(t: DataDBJsonModel[Boolean]) = DataDBBsonBooleanHandler.write(t)
}