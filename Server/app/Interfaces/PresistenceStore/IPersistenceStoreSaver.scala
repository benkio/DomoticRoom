package Interfaces.PresistenceStore

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreSaver
  extends IPersistenceStoreDataSaver
    with IPersistenceStoreRangeSaver {

}
