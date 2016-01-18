package models.persistenceStore.loaders

import interfaces.presistenceStore.IPersistenceStoreDataLoader
import org.joda.time.{Interval, DateTime}
import reactivemongo.api._

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
class PersistenceStoreDataLoader(val reactiveMongoApi : ReactiveMongoApi) extends IPersistenceStoreDataLoader with ReactiveMongoComponents{

  override def loadData(sensorName: String, startDate: DateTime, timeInterval: Interval): Unit = ??? //TODO

  override def loadCurrentSensorsData(): Unit = ??? //TODO

  override def loadCurrentSensorData(sensorName: String): Unit = ??? //TODO
}
