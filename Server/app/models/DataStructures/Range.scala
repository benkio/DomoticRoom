package models.DataStructures

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */

case class Range(minBound: Double, maxBound:Double, rt: Int)

object RangeType extends Enumeration {
  type RangeType = Value
  val Gas, Light, Movement, Temperature, Humidity = Value
}

object RangeBooleanDBJson {
  val Id = "_id"
  val value = "value"
  val rangeType = "type"
  val dateCreated = "dateCreated"
}

object RangeDBJson {
  val Id = "_id"
  val minBound = "minBound"
  val maxBound = "maxBound"
  val rangeType = "type"
  val dateCreated = "dateCreated"
}

object RangeDBCollection {
  val name = "Ranges"
}