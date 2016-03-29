package models.persistenceStore

import interfaces.presistenceStore._
import models.DataStructures.RangeModel.RangeType
import models.persistenceStore.loaders._
import models.persistenceStore.savers._
import org.joda.time.{DateTime, ReadableDuration}
import play.api.Play.current
import play.api.libs.iteratee.Enumerator
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.BSONDocument

import scala.concurrent.duration.{Duration, FiniteDuration}

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

  override def loadCurrentSensorDataContinuously(duration : FiniteDuration): Enumerator[BSONDocument] =
    psl.loadCurrentSensorDataContinuously(duration)

  override def loadCurrentSensorData(sensorName: String) =
    psl.loadCurrentSensorData(sensorName)

  override def loadLastRange(rangeType: RangeType.Value) =
    psl.loadLastRange(rangeType)

  override def loadRange(rangeType: RangeType.Value, startDate: DateTime, duration: ReadableDuration) =
    psl.loadRange(rangeType, startDate, duration)

  override def loadLastRanges =
    psl.loadLastRanges

  override def loadSensors =
    psl.loadSensors

  //////////////////////////////////////////////
  ////////////////Save Operations///////////////
  //////////////////////////////////////////////

  override def saveDataWithRangeException(data:BSONDocument) =
    pss.saveDataWithRangeException(data)

  override def saveRange(data: BSONDocument) =
    pss.saveRange(data)

  override def saveData(data:BSONDocument) =
    pss.saveData(data)
}


object ReactiveMongoInjector {
  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  def collection(name: String): reactivemongo.play.json.collection.JSONCollection =
    reactiveMongoApi.db.collection[reactivemongo.play.json.collection.JSONCollection](name)
}