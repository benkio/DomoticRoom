package models.persistenceStore.savers

import interfaces.presistenceStore.IPersistenceStoreRangeSaver
import models.DataStructures.RangeModel.RangeDBJsonModel
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument


/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreRangeSaver (val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStoreRangeSaver with ReactiveMongoComponents {

  val rangeCollection = reactiveMongoApi.db.collection[BSONCollection](RangeDBJsonModel.RangeDBCollectionName)

  override def saveRange(dataFormatted : BSONDocument) = {
    StoreUtils.store(rangeCollection,dataFormatted)
  }
}