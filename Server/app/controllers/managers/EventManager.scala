package controllers.managers

import interfaces.managers.{IRangeChecker, IEventManager}
import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson.DataReceivedJsonModel
import models.DataStructures.RangeModel.RangeBoolean
import models.DataStructures.SensorModel
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.iteratee.Enumeratee
import reactivemongo.bson.BSONObjectID

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object EventManager extends IEventManager{

  val rangeChecker: IRangeChecker = RangeCheckerFactory.apply

  override def newRange(rangeBoolean: RangeBoolean) = rangeChecker.updateRange(rangeBoolean)

  override def newRange(range: models.DataStructures.RangeModel.Range) = {
    rangeChecker.updateRange(range)
  }

  override def newData: Enumeratee[DataReceivedJsonModel, DataDBJsonModel] = {
    Enumeratee.map[DataReceivedJsonModel](x => DataDBJsonModel(BSONObjectID.generate.toString(),x.dateCreation,rangeChecker.checkRange(x.value,SensorModel.intToSensorType(x.dataType.toInt)),x.sensorName,x.dataType,x.value))
  }
}