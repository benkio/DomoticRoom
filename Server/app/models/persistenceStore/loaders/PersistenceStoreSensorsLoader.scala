package models.persistenceStore.loaders

import javax.inject.Inject

import interfaces.presistenceStore.IPersistenceStoreSensorsLoader
import models.DataStructures.SensorDBJson
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreSensorsLoader  @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends IPersistenceStoreSensorsLoader
    with ReactiveMongoComponents{

  val sensorsCollection = reactiveMongoApi.db.collection[BSONCollection](SensorDBJson.SensorDBCollectionName)

  override def loadSensors = {
    sensorsCollection.find(BSONDocument()).sort(BSONDocument(SensorDBJson.id -> -1)).cursor[BSONDocument]().enumerate(25)
  }
}