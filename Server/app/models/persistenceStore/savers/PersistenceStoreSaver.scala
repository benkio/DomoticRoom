package models.persistenceStore.savers

import interfaces.presistenceStore.{IPersistenceStoreDataSaver, IPersistenceStoreRangeSaver, IPersistenceStoreSaver}
import reactivemongo.bson.BSONDocument

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
