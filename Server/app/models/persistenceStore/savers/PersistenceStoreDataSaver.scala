package models.persistenceStore.savers

import javax.inject.Inject

import interfaces.presistenceStore.IPersistenceStoreDataSaver
import models.DataStructures.DataReceivedJson$
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataSaver @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStoreDataSaver with ReactiveMongoComponents {

  val dataCollection = reactiveMongoApi.db.collection[BSONCollection](DataReceivedJson.DataDBCollectionName)

  override def saveData(dataFormatted : BSONDocument) = {
    StoreUtils.store(dataCollection,dataFormatted)
  }


  override def saveDataWithRangeException(dataFormatted : BSONDocument) = {
    StoreUtils.store(dataCollection,dataFormatted)
  }
}