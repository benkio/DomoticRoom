package models.persistenceStore.savers

import interfaces.presistenceStore.{IPersistenceStoreSaver, IPersistenceStoreRangeSaver, IPersistenceStoreDataSaver}
import models.SensorType
import play.api.libs.json.JsValue
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.util.{Success, Failure}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreSaver(psrs:IPersistenceStoreRangeSaver, psds:IPersistenceStoreDataSaver) extends IPersistenceStoreSaver{

  //////////////////////////////////////////////
  ////////////////Save Range Operations/////////
  //////////////////////////////////////////////
  override def saveRange(dataFormatted: BSONDocument) =
    psrs.saveRange(dataFormatted)

  //////////////////////////////////////////////
  ////////////////Save Data Operations//////////
  //////////////////////////////////////////////

  override def saveDataWithRangeException(data: BSONDocument) =
    psds.saveDataWithRangeException(data)

  override def saveData(data: BSONDocument) =
    psds.saveData(data)

}
