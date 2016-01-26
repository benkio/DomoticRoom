package interfaces.dataFormatter

import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IDBDataFormatter extends IDataFormatter{
  override def getFormatterStreamStep :Enumeratee[JsValue,BSONDocument]
}
