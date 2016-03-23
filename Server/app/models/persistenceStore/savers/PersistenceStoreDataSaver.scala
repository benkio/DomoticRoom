package models.persistenceStore.savers

import javax.inject.Inject

import interfaces.presistenceStore.IPersistenceStoreDataSaver
import models.DataStructures.DataDBJson
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._
import reactivemongo.bson.BSONDocument

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataSaver @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStoreDataSaver with ReactiveMongoComponents {

  val dataCollection = reactiveMongoApi.db.collection[JSONCollection](DataDBJson.DataDBCollectionName)

  override def saveData(dataFormatted : BSONDocument) = {
    StoreUtils.store(dataCollection,dataFormatted)
  }


  override def saveDataWithRangeException(dataFormatted : BSONDocument) = {
    StoreUtils.store(dataCollection,dataFormatted)
  }
}