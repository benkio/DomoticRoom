package models.persistenceStore

import interfaces.presistenceStore._
import models.persistenceStore.loaders._
import models.persistenceStore.savers._
import models.{SensorType, Range}
import org.joda.time.{Interval, DateTime}
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
object PersistenceStore @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends IPersistenceStore with ReactiveMongoComponents {
  val pss: IPersistenceStoreSaver = new PersistenceStoreSaver(
    new PersistenceStoreRangeSaver(reactiveMongoApi),
    new PersistenceStoreDataSaver()
  )
  val psl : IPersistenceStoreLoader = new PersistenceStoreLoader(
    new PersistenceStoreDataLoader(),
    new PersistenceStoreRangeLoader(),
    new PersistenceStoreSensorsLoader())

  //////////////////////////////////////////////
  ////////////////Load Operations///////////////
  //////////////////////////////////////////////

  override def loadData(sensorName: String, startDate: DateTime, timeInterval: Interval): Unit =
    psl.loadData(sensorName, startDate, timeInterval)

  override def loadCurrentSensorsData(): Unit =
    psl.loadCurrentSensorsData()

  override def loadCurrentSensorData(sensorName: String): Unit =
    psl.loadCurrentSensorData(sensorName)

  override def loadLastRange(sensorType: SensorType): Unit =
    psl.loadLastRange(sensorType)

  override def loadRange(sensorType: SensorType, startDate: DateTime, timeInterval: Interval): Unit =
    psl.loadRange(sensorType, startDate, timeInterval)

  override def loadLastRanges: Unit =
    psl.loadLastRanges

  override def loadSensors: Unit =
    psl.loadSensors

  //////////////////////////////////////////////
  ////////////////Save Operations///////////////
  //////////////////////////////////////////////

  override def saveWithRangeException(data: JsValue, sensorName: String, range: Range, sensorType: SensorType, delta: Double): Unit =
    pss.saveWithRangeException(data, sensorName, range, sensorType, delta)

  override def save(data: JsValue, sensorName: String, sensorType: SensorType): Unit =
    pss.save(data,sensorName,sensorType)

  override def save(range: Range, sensorType: SensorType): Unit =
    pss.save(range,sensorType)
}
