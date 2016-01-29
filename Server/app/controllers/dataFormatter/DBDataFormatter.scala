package controllers.dataFormatter

import interfaces.dataFormatter.IDBDataFormatter
import models.DataStructures.RangeModel.{RangeBoolean, RangeDBJsonModel}
import models.DataStructures.DataDBJson
import models.DataStructures.DataDBJson.DataDBJsonModel
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class DBDataFormatter extends IDBDataFormatter {

  override def getFormatterStreamStep = {
    val convertToBson : Enumeratee[JsValue,BSONDocument]=
      Enumeratee.map[JsValue](in => DataDBJson.toBsonDocument(in.validate[DataDBJsonModel].get))

    val ValidateJson : Enumeratee[JsValue,JsValue] =
        Enumeratee.filter(input => DataDBJson.validateJsonData(input))

    ValidateJson ><> convertToBson
  }

  override def convertToBson(range : Range): BSONDocument = {
    RangeDBJsonModel
    ??? // TODO
  }

  override def convertToBson(range: RangeBoolean): BSONDocument = ??? //TODO
}
