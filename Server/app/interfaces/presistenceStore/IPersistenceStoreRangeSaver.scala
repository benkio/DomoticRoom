package interfaces.presistenceStore

import models.SensorType

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */
trait IPersistenceStoreRangeSaver {
  def save(range: models.Range, sensorType: SensorType)
}
