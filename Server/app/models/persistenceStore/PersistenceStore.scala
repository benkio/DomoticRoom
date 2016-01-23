package models.persistenceStore

import interfaces.presistenceStore._
import models.persistenceStore.loaders._
import models.persistenceStore.savers._
import models.{RangeType, SensorType, Range}
import org.joda.time.{ReadableDuration, Interval, DateTime}
import play.api.libs.json.JsValue

import play.api.Play.current

import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
object PersistenceStore extends IPersistenceStore{

  val pss: IPersistenceStoreSaver = new PersistenceStoreSaver(
    new PersistenceStoreRangeSaver(ReactiveMongoInjector.reactiveMongoApi),
    new PersistenceStoreDataSaver(ReactiveMongoInjector.reactiveMongoApi)
  )
  val psl : IPersistenceStoreLoader = new PersistenceStoreLoader(
    new PersistenceStoreDataLoader(ReactiveMongoInjector.reactiveMongoApi),
    new PersistenceStoreRangeLoader(ReactiveMongoInjector.reactiveMongoApi),
    new PersistenceStoreSensorsLoader(ReactiveMongoInjector.reactiveMongoApi))

  //////////////////////////////////////////////
  ////////////////Load Operations///////////////
  //////////////////////////////////////////////

  override def loadData(sensorName: String, startDate: DateTime, duration:ReadableDuration) =
    psl.loadData(sensorName, startDate, duration)

  override def loadCurrentSensorsData() =
    psl.loadCurrentSensorsData()

  override def loadCurrentSensorData(sensorName: String) =
    psl.loadCurrentSensorData(sensorName)

  override def loadLastRange(rangeType: RangeType) =
    psl.loadLastRange(rangeType)

  override def loadRange(rangeType: RangeType, startDate: DateTime, duration: ReadableDuration) =
    psl.loadRange(rangeType, startDate, duration)

  override def loadLastRanges =
    psl.loadLastRanges

  override def loadSensors =
    psl.loadSensors

  //////////////////////////////////////////////
  ////////////////Save Operations///////////////
  //////////////////////////////////////////////

  override def saveWithRangeException(data: JsValue, sensorName: String, range: Range, sensorType: SensorType, delta: Double) =
    pss.saveWithRangeException(data, sensorName, range, sensorType, delta)

  override def save(data: JsValue, sensorName: String, sensorType: SensorType) =
    pss.save(data,sensorName,sensorType)

  override def save(range: Range, sensorType: SensorType) =
    pss.save(range,sensorType)
}


object ReactiveMongoInjector {
  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  def collection(name: String): JSONCollection =
    reactiveMongoApi.db.collection[JSONCollection](name)
}