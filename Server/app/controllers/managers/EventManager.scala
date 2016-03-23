package controllers.managers

import javassist.bytecode.stackmap.TypeTag

import interfaces.managers.{IRangeChecker, IEventManager}
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson._
import models.DataStructures.RangeModel.RangeBoolean
import models.DataStructures.SensorModel
import org.joda.time.DateTime
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

  override def newDataDouble: Enumeratee[DataReceivedDoubleJsonModel, DataDBJsonModel[Double]] = {
    Enumeratee.map[DataReceivedDoubleJsonModel](y =>
        DataDBJsonModel(BSONObjectID.generate,y.dateCreation,rangeChecker.checkRange(y.value,SensorModel.intToSensorType(y.sensorType.toInt)),y.sensorName,y.sensorType,y.value))
  }

  override def newDataBoolean: Enumeratee[DataReceivedBooleanJsonModel, DataDBJsonModel[Boolean]] = {
    Enumeratee.map[DataReceivedBooleanJsonModel](y =>
      DataDBJsonModel(BSONObjectID.generate,y.dateCreation,rangeChecker.checkRange(y.value,SensorModel.intToSensorType(y.sensorType.toInt)),y.sensorName,y.sensorType,y.value))
  }
}