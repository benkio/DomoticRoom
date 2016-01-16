package Interfaces.PresistenceStore

import models.SensorType

/**
  * Created by parallels on 1/16/16.
  */
trait IPersistenceStoreRangeSaver {
  def save(range: models.Range, sensorType: SensorType)
}
