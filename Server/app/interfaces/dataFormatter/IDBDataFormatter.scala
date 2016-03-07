package interfaces.dataFormatter

import models.DataStructures.RangeModel.RangeBoolean
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IDBDataFormatter extends IDataFormatter{
  def convertToBson(range : models.DataStructures.RangeModel.Range) : BSONDocument
  def convertToBson(range : RangeBoolean) : BSONDocument
  def getFormatterStreamStep : Enumeratee[JsValue, BSONDocument]
}
