package models

/**
  * Created by parallels on 1/16/16.
  */
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
