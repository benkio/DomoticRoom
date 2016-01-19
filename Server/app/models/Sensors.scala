package models

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */

case class Sensor(Id: Int, sensorType: Int)

//{
//val sensorType:SensorType = SensorTypeUtil.Int2SensorType(rt)
//}

class SensorType

case class TemperatureSensorType() extends SensorType

case class GasSensorType() extends SensorType

case class MovementSensorType() extends SensorType

case class LightSensorType() extends SensorType

case class HumiditySensorType() extends SensorType

object SensorTypeUtil {
  def Int2SensorType(rt: Int): SensorType = rt match {
    case 1 => new GasSensorType
    case 2 => new LightSensorType
    case 3 => new MovementSensorType
    case 4 => new TemperatureSensorType
    case 5 => new HumiditySensorType
  }

  def SensorType2Int(sensorType: SensorType) = {
    sensorType match {
      case GasSensorType() => 1
      case LightSensorType() => 2
      case MovementSensorType() => 3
      case TemperatureSensorType() => 4
      case HumiditySensorType() => 5
    }
  }
}