package interfaces.presistenceStore

import org.joda.time.{DateTime, Interval}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreDataLoader {
  def loadData(sensorName: String, startDate:DateTime, timeInterval: Interval)
  def loadCurrentSensorData(sensorName : String)
  def loadCurrentSensorsData()
}
