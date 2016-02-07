package models.DataStructures

import models.DataStructures.RangeModel.RangeType
import models.DataStructures.RangeModel.RangeType.RangeType
import models.DataStructures.SensorModel.SensorType.SensorType

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */

object SensorModel {

  case class Sensor(Id: Int, sensorType: Int)

  object SensorType extends Enumeration {
    type SensorType = Value
    val Gas, Light, Movement, Temperature, Humidity = Value
  }

  object SensorDBJson {
    val id = "_id"
    val sensorName = "sensorName"
    val sensorType = "type"
    val SensorDBCollectionName = "Sensors"
  }

  // RangeType TYPE

  def sensorTypeToRangeType(sensorType : SensorType): RangeType = sensorType match {
    case SensorType.Gas => RangeType.Gas
    case SensorType.Humidity => RangeType.Humidity
    case SensorType.Light => RangeType.Light
    case SensorType.Movement => RangeType.Movement
    case SensorType.Temperature => RangeType.Temperature
  }

  // INTEGER

  def intToSensorType(id : Int): SensorType = id match {
    case 1 => SensorType.Gas
    case 2 => SensorType.Humidity
    case 3 => SensorType.Light
    case 4 => SensorType.Movement
    case 5 => SensorType.Temperature
  }
}