package models.DataStructures

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */

case class Sensor(Id: Int, sensorType: Int)

object SensorType extends Enumeration {
  type SensorType = Value
  val Gas, Light, Movement, Temperature, Humidity = Value
}

object SensorDBJson {
  val id = "_id"
  val sensorName = "sensorName"
  val sensorType = "type"
}

object SensorDBCollection {
  val Name = "Sensors"
}