package interfaces.presistenceStore

import models.DataStructures.SensorType
import play.api.libs.json.JsValue
import play.libs.F.Tuple
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreDataSaver {
  def saveData(dataFormatted : BSONDocument)
  def saveDataWithRangeException(dataFormatted : BSONDocument)
}