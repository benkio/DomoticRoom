package models.persistenceStore.savers

import interfaces.presistenceStore.{IPersistenceStoreSaver, IPersistenceStoreRangeSaver, IPersistenceStoreDataSaver}
import models.{Range, SensorType}
import play.api.libs.json.JsValue
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreSaver(psrs:IPersistenceStoreRangeSaver, psds:IPersistenceStoreDataSaver) extends IPersistenceStoreSaver{

  //////////////////////////////////////////////
  ////////////////Save Range Operations/////////
  //////////////////////////////////////////////
  override def save(range: Range, sensorType: SensorType): Unit =
    psrs.save(range,sensorType)

  //////////////////////////////////////////////
  ////////////////Save Data Operations//////////
  //////////////////////////////////////////////

  override def saveWithRangeException(data: BSONDocument) =
    psds.saveWithRangeException(data)

  override def save(data: BSONDocument) =
    psds.save(data)
}
