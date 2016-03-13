package controllers.managers


import models.DataStructures.DataDBJson.DataRangeViolationDBJson
import models.DataStructures.RangeModel.RangeBoolean

import scala.collection.mutable
import scala.collection.mutable.HashMap
import interfaces.managers.IRangeChecker
import models.DataStructures
import models.DataStructures.RangeModel
import models.DataStructures.SensorModel.SensorType
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object RangeCheckerFactory {

  private class RangeChecker extends IRangeChecker {
    var currentRanges = new HashMap[SensorType.Value, DataStructures.RangeModel.IRange]()

    override def checkRange(data: Any , sensorType: SensorType.Value): Option[models.DataStructures.DataDBJson.DataRangeViolationDBJson] = {
      currentRanges.get(sensorType) match {
        case Some(x)  => x match {
          case y : RangeModel.Range => data match {
            case z : Double if (z > y.maxBound) => Some(DataRangeViolationDBJson(z-y.maxBound))
            case z : Double if (z < y.minBound) => Some(DataRangeViolationDBJson(y.maxBound-z))
            case _ => None
          }
          case y : RangeBoolean => if (data == y.value) None else Some(DataRangeViolationDBJson(0))
        }
        case None => None
      }
    }

    override def updateRange(range: DataStructures.RangeModel.Range): Unit = {
      currentRanges += (models.DataStructures.RangeModel.rangeTypeToSensorType(RangeModel.intToRangeType(range.rt)) -> range)
    }

    override def updateRange(rangeBoolean: RangeBoolean): Unit = {
      currentRanges += (models.DataStructures.RangeModel.rangeTypeToSensorType(RangeModel.intToRangeType(rangeBoolean.rt)) -> rangeBoolean)
    }
  }

  def apply : IRangeChecker = new RangeChecker
}
