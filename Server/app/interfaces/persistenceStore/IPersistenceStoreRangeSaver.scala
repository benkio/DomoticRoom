package interfaces.presistenceStore

import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreRangeSaver {
  def saveRange(dataFormatted : BSONDocument)
}
