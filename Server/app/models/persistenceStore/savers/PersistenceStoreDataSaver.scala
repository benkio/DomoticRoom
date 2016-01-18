package models.persistenceStore.savers

import interfaces.presistenceStore.IPersistenceStoreDataSaver
import models.{SensorType, Range}
import play.api.libs.json.JsValue

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{ Action, BodyParsers, Call, Controller, Result }

import reactivemongo.bson.{ BSONObjectID, BSONDocument }
import reactivemongo.core.actors.Exceptions.PrimaryUnavailableException
import reactivemongo.api.commands.WriteResult

import play.modules.reactivemongo.{
  MongoController, ReactiveMongoApi, ReactiveMongoComponents
}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
class PersistenceStoreDataSaver @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStoreDataSaver with ReactiveMongoComponents {
  override def save(data: JsValue, sensorName: String, sensorType: SensorType): Unit = ??? //TODO

  override def saveWithRangeException(data: JsValue, sensorName: String, range: Range, sensorType: SensorType, delta: Double): Unit = ??? //TODO
}
