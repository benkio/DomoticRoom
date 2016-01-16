package models

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */

case class Sensor(Id: Int, sensorType: Int) //{
//val sensorType:SensorType = SensorTypeUtil.Int2SensorType(rt)
//}

class SensorType

case class TemperatureSensorType() extends SensorType
case class GasSensorType()         extends SensorType
case class MovementSensorType()    extends SensorType
case class LightSensorType()       extends SensorType

object SensorTypeUtil{
  def Int2SensorType(rt:Int): SensorType = rt match {
    case 1 => new GasSensorType
    case 2 => new LightSensorType
    case 3 => new MovementSensorType
    case 4 => new TemperatureSensorType
  }
}
