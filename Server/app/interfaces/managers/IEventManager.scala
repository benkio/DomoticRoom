package interfaces.managers

import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson._
import play.api.libs.iteratee.{Enumeratee}

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IEventManager {
  def newDataDouble: Enumeratee[DataReceivedDoubleJsonModel,DataDBJsonModel[Double]]
  def newDataBoolean: Enumeratee[DataReceivedBooleanJsonModel,DataDBJsonModel[Boolean]]
  def newRange(range: models.DataStructures.RangeModel.Range)
  def newRange(range: models.DataStructures.RangeModel.RangeBoolean)
}
