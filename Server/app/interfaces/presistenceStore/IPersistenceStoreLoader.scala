package interfaces.presistenceStore

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreLoader
  extends IPersistenceStoreDataLoader
    with IPersistenceStoreSensorsLoader
    with IPersistenceStoreRangeLoader {

}
