package Interfaces.PresistenceStore

import models.SensorType
import org.joda.time.{DateTime, Interval}

/**
  * Created by parallels on 1/16/16.
  */
trait IPersistenceStoreRangeLoader {
  def loadRange(sensorType: SensorType, startDate: DateTime, timeInterval: Interval)
  def loadLastRanges
  def loadLastRange(sensorType: SensorType)
}
