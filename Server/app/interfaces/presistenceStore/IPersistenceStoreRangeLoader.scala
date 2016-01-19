package interfaces.presistenceStore

import models.{RangeType, SensorType}
import org.joda.time.{ReadableDuration, DateTime, Interval}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreRangeLoader {
  def loadRange(rangeType: RangeType, startDate: DateTime, duration: ReadableDuration)
  def loadLastRanges
  def loadLastRange(sensorType: SensorType)
}
