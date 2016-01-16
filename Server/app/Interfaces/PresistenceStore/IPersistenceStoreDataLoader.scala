package Interfaces.PresistenceStore

import org.joda.time.{DateTime, Interval}

/**
  * Created by parallels on 1/16/16.
  */
trait IPersistenceStoreDataLoader {
  def loadData(sensorName: String, startDate:DateTime, timeInterval: Interval)
  def loadCurrentSensorData(sensorName : String)
  def loadCurrentSensorsData()
}
