package controllers.dataFormatter

import interfaces.dataFormatter.IDBDataFormatter
import models.DataStructures.DataDBJson
import models.DataStructures.RangeModel._
import models.DataStructures.DataDBJson._
import org.joda.time.DateTime
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson.{BSONObjectID, BSONDocument}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object DBDataFormatter extends IDBDataFormatter {

    override def getFormatterStreamStep = {
      val convertToBson: Enumeratee[JsValue, BSONDocument] =
        Enumeratee.map[JsValue](in => DataDBJson.toBsonDocument(in.validate[DataDBJsonModel].get))

      val ValidateJson: Enumeratee[JsValue, JsValue] =
        Enumeratee.filter(input => DataDBJson.validateJsonData(input))

      ValidateJson ><> convertToBson
    }

    override def convertToBson(range: Range): BSONDocument = {
      val rangeDB = RangeDBJson(BSONObjectID.generate, range.minBound, range.maxBound, range.rt, DateTime.now)
      RangeDBBsonHandler.write(rangeDB)
    }

    override def convertToBson(range: RangeBoolean): BSONDocument = {
      val rangeDB = RangeBooleanDBJson(BSONObjectID.generate, range.value, range.rt, DateTime.now)
      RangeBooleanDBBsonHandler.write(rangeDB)
    }
}