package models.persistenceStore.savers

import interfaces.presistenceStore.IPersistenceStoreDataSaver
import models.DataStructures.DataDBCollection
import models.SensorType
import play.api.libs.json.JsValue

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{ Action, BodyParsers, Call, Controller, Result }
import reactivemongo.api.collections.bson.BSONCollection

import reactivemongo.bson.{ BSONObjectID, BSONDocument }
import reactivemongo.core.actors.Exceptions.PrimaryUnavailableException
import reactivemongo.api.commands.WriteResult

import play.modules.reactivemongo.{
  MongoController, ReactiveMongoApi, ReactiveMongoComponents
}

import scala.util.{Success, Failure}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataSaver @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStoreDataSaver with ReactiveMongoComponents {

  val dataCollection = reactiveMongoApi.db.collection[BSONCollection](DataDBCollection.name)

  override def saveData(dataFormatted : BSONDocument) = {
    StoreUtils.store(dataCollection,dataFormatted)
  }


  override def saveDataWithRangeException(dataFormatted : BSONDocument) = {
    StoreUtils.store(dataCollection,dataFormatted)
  }
}