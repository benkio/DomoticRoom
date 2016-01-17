package interfaces.presistenceStore

/**
  * Created by parallels on 1/16/16.
  */
trait IPersistenceStoreLoader
  extends IPersistenceStoreDataLoader
    with IPersistenceStoreSensorsLoader
    with IPersistenceStoreRangeLoader {

}
