package controllers.managers


import interfaces.managers.IRangeChecker
import models.DataStructures.DataDBJson.DataRangeViolationDBJson
import models.DataStructures.RangeModel.{RangeBooleanDBJsonModel, RangeBoolean, RangeDBJsonModel}
import models.DataStructures.SensorModel.SensorType
import models.DataStructures._
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.mutable.HashMap

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object RangeCheckerFactory {

  private class RangeChecker extends IRangeChecker {
    var currentRanges = new HashMap[SensorType.Value, RangeModel.IRange]()

    (PersistenceStore.loadLastRanges &> Enumeratee.filter(x => RangeModel.isBoolean(RangeModel.intToRangeType(x.getAs[Int](RangeBooleanDBJsonModel.rangeType).get))))(Iteratee.foreach[BSONDocument](x =>
      currentRanges +=
        SensorModel.intToSensorType(x.getAs[Int](RangeBooleanDBJsonModel.rangeType).get) ->
          RangeBoolean(
              x.getAs[Boolean](RangeBooleanDBJsonModel.value).get,
              x.getAs[Int](RangeBooleanDBJsonModel.rangeType).get)))

    (PersistenceStore.loadLastRanges &> Enumeratee.filter(x => !RangeModel.isBoolean(RangeModel.intToRangeType(x.getAs[Int](RangeDBJsonModel.rangeType).get))))(Iteratee.foreach[BSONDocument](x =>
      currentRanges +=
        SensorModel.intToSensorType(x.getAs[Int](RangeDBJsonModel.rangeType).get) ->
          RangeModel.Range(
            x.getAs[Double](RangeDBJsonModel.minBound).get,
            x.getAs[Double](RangeDBJsonModel.maxBound).get,
            x.getAs[Int](RangeDBJsonModel.rangeType).get)))

    override def checkRange(data: Any , sensorType: SensorType.Value): Option[models.DataStructures.DataDBJson.DataRangeViolationDBJson] = {
      currentRanges.get(sensorType) match {
        case Some(x)  => x match {
          case y : RangeModel.Range => data match {
            case z : Double if (z > y.maxBound) => Some(DataRangeViolationDBJson(z-y.maxBound))
            case z : Double if (z < y.minBound) => Some(DataRangeViolationDBJson((y.minBound-z)*(-1)))
            case _ => None
          }
          case y : RangeBoolean => if (data == y.value) None else Some(DataRangeViolationDBJson(0))
        }
        case None => None
      }
    }

    override def updateRange(range: RangeModel.Range): Unit = {
      currentRanges += (models.DataStructures.RangeModel.rangeTypeToSensorType(RangeModel.intToRangeType(range.rt)) -> range)
    }

    override def updateRange(rangeBoolean: RangeBoolean): Unit = {
      currentRanges += (models.DataStructures.RangeModel.rangeTypeToSensorType(RangeModel.intToRangeType(rangeBoolean.rt)) -> rangeBoolean)
    }
  }

  def apply : IRangeChecker = new RangeChecker
}
