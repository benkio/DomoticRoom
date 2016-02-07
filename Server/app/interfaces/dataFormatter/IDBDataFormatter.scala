package interfaces.dataFormatter

import models.DataStructures.RangeModel.RangeBoolean
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IDBDataFormatter extends IDataFormatter{
  def convertToBson(range : models.DataStructures.RangeModel.Range) : BSONDocument
  def convertToBson(range : RangeBoolean) : BSONDocument
}
