package models.DataStructures

import play.api.libs.json._
import reactivemongo.bson._
import reactivemongo.play.json._
import reflect.runtime.universe._

import scala.util.Success

/**
  * Created by parallels on 1/26/16.
  */

object DataDBJson {
  import org.joda.time.DateTime

  val ISO8601 = "yyyy-MM-dd HH:mm:ss"
  //Fields
  val id = "_id"
  val dateCreation = "dateCreation"
  val rangeViolation = "rangeViolation"
  val rangeViolationDelta = "delta"
  val sensorName = "sensorName"
  val dataType = "type"
  val value = "value"
  val DataDBCollectionName = "Data"

  //////////////////////////////////////////
  //////////// Data Patterns ///////////////
  //////////////////////////////////////////

  case class DataRangeViolationDBJson(delta : Double)
  case class DataDBJsonModel[T](id : BSONObjectID,
                             dateCreation: DateTime,
                             rangeViolation : Option[DataRangeViolationDBJson],
                             sensorName : String,
                             dataType : Double,
                             value : T)

  case class DataAnalizeDBJson(value : Double)

  //////////////////////////////////////////
  //////////// JSON - BSON Convertors //////
  //////////////////////////////////////////

  implicit val rangeViolationDBBsonHandler =  Macros.handler[DataRangeViolationDBJson]

  implicit val rangeViolationDBJsonReader : Reads[DataRangeViolationDBJson] =
    (JsPath \ rangeViolationDelta).read[Double].map(DataRangeViolationDBJson)

  implicit val rangeViolationDBJsonWrites = new Writes[Option[DataRangeViolationDBJson]] {
    def writes(data: Option[DataRangeViolationDBJson]) = data match {
      case Some(d) => Json.obj(
        rangeViolationDelta -> d.delta
      )
      case None => JsString("")
    }
  }


  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  implicit val dateFormat =
    Format[DateTime](Reads.jodaDateReads(ISO8601), Writes.jodaDateWrites(ISO8601))

  implicit object DataDBBsonDoubleHandler extends BSONDocumentReader[DataDBJsonModel[Double]] with BSONDocumentWriter[DataDBJsonModel[Double]] {
    def read(bson: BSONDocument): DataDBJsonModel[Double] = DataDBJsonModel[Double](
      bson.getAs[BSONObjectID](id).get,
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
      bson.getAs[BSONObjectID](id).get,
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


  implicit def DataDBJsonModelViolationReader[T](implicit fmt: Reads[T]): Reads[DataDBJsonModel[T]] = new Reads[DataDBJsonModel[T]] {
    def reads(json: JsValue): JsResult[DataDBJsonModel[T]] = JsSuccess(new DataDBJsonModel[T] (
      (json \ id).as[BSONObjectID],
      (json \ dateCreation).as[DateTime],
      (json \ rangeViolation).asOpt[DataRangeViolationDBJson],
      (json \ sensorName).as[String],
      (json \ dataType).as[Double],
      (json \ value).as[T]
    )
    )
  }

  implicit def DataJsonModelWriter[T](implicit fmt: Writes[T]): Writes[DataDBJsonModel[T]] = new Writes[DataDBJsonModel[T]] {
      def writes(ts: DataDBJsonModel[T]) = JsObject(Seq(
      id -> JsString(BSONObjectID.generate.toString()),
      dateCreation -> Json.toJson(ts.dateCreation),
      rangeViolation -> Json.toJson[Option[DataRangeViolationDBJson]](ts.rangeViolation),
      sensorName -> JsString(ts.sensorName),
      dataType -> JsNumber(ts.dataType),
      value -> Json.toJson[T](ts.value)
    ))
  }

  def validateJsonData(Json: JsValue) = {
    (Json.validate[DataDBJsonModel[Double]].isSuccess, Json.validate[DataDBJsonModel[Boolean]].isSuccess) match {
      case (true, _)  => Json.validate[DataDBJsonModel[Double]].get
      case (_, true)  => Json.validate[DataDBJsonModel[Boolean]].get
      case _          => throw new Exception
    }
  }

  def validateBsonDocument(bson: BSONDocument) : DataDBJsonModel[_] = {
    (DataDBBsonDoubleHandler.readTry(bson), DataDBBsonBooleanHandler.readTry(bson)) match {
      case (Success(x),_) => x
      case (_,Success(x)) => x
      case _              => throw new Exception
    }
  }

  def DataJsonModelToJson(t: DataDBJsonModel[_]) = t.value match {
    case x if x.isInstanceOf[Double]  => DataJsonModelWriter[Double].writes(new DataDBJsonModel[Double](t.id,t.dateCreation,t.rangeViolation,t.sensorName,t.dataType, t.value.asInstanceOf[Double]))
    case x if x.isInstanceOf[Boolean] => DataJsonModelWriter[Boolean].writes(new DataDBJsonModel[Boolean](t.id,t.dateCreation,t.rangeViolation,t.sensorName,t.dataType, t.value.asInstanceOf[Boolean]))
    case _ => JsString("WTF")
  }

  def DataJsonModelToBson[T: TypeTag](t: DataDBJsonModel[T]) = t match {
    case x if typeOf[T] <:< typeOf[Double]  => DataDBBsonDoubleHandler.write(new DataDBJsonModel[Double](x.id,x.dateCreation,x.rangeViolation,x.sensorName,x.dataType, x.value.asInstanceOf[Double]))
    case x if typeOf[T] <:< typeOf[Boolean] => DataDBBsonBooleanHandler.write(new DataDBJsonModel[Boolean](x.id,x.dateCreation,x.rangeViolation,x.sensorName,x.dataType, x.value.asInstanceOf[Boolean]))
  }
}