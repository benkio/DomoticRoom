package models.persistenceStore.loaders

import javax.inject.Inject

import interfaces.presistenceStore.IPersistenceStoreSensorsLoader
import models.DataStructures.SensorModel.SensorDBJson
import play.api.libs.json.{JsNumber, JsObject}
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json._

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreSensorsLoader  @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends IPersistenceStoreSensorsLoader
    with ReactiveMongoComponents{

  val sensorsCollection = reactiveMongoApi.db.collection[JSONCollection](SensorDBJson.SensorDBCollectionName)

  override def loadSensors = {
    sensorsCollection.find(BSONDocument()).sort(JsObject(Seq(SensorDBJson.id -> JsNumber(-1)))).cursor[BSONDocument]().enumerate(25)
  }
}