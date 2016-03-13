package controllers.managers

import javassist.bytecode.stackmap.TypeTag

import interfaces.managers.{IRangeChecker, IEventManager}
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson.DataReceivedJsonModel
import models.DataStructures.RangeModel.RangeBoolean
import models.DataStructures.SensorModel
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.iteratee.Enumeratee
import reactivemongo.bson.BSONObjectID
import scala.reflect.runtime.universe._

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object EventManager extends IEventManager{

  val rangeChecker: IRangeChecker = RangeCheckerFactory.apply

  override def newRange(rangeBoolean: RangeBoolean) = rangeChecker.updateRange(rangeBoolean)

  override def newRange(range: models.DataStructures.RangeModel.Range) = {
    rangeChecker.updateRange(range)
  }

  override def newData[T]: Enumeratee[DataReceivedJsonModel[T], DataDBJsonModel[T]] = {
    Enumeratee.map[DataReceivedJsonModel[T]](y =>
        DataDBJsonModel(BSONObjectID.generate.toString(),y.dateCreation,rangeChecker.checkRange(y.value,SensorModel.intToSensorType(y.dataType.toInt)),y.sensorName,y.dataType,y.value))
  }
}