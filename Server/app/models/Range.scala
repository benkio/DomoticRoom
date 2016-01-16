package models

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/16/16.
  */

case class Range(minBound: Double, maxBound:Double, rt: Int) //{
//val rangeType:RangeType = RangeTypeUtil.Int2RangeType(rt)
//}

class RangeType

case class TemperatureRangeType() extends RangeType
case class GasRangeType()         extends RangeType
case class MovementRangeType()    extends RangeType
case class LightRangeType()       extends RangeType

object RangeTypeUtil{
  def Int2RangeType(rt:Int): RangeType = rt match {
    case 1 => new GasRangeType
    case 2 => new LightRangeType
    case 3 => new MovementRangeType
    case 4 => new TemperatureRangeType
  }
}
