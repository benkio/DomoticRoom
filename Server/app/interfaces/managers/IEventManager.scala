package interfaces.managers

import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson.DataReceivedJsonModel
import play.api.libs.iteratee.{Iteratee, Enumeratee}
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IEventManager {
  def newData[T]: Enumeratee[DataReceivedJsonModel[T],DataDBJsonModel[T]]
  def newRange(range: models.DataStructures.RangeModel.Range)
  def newRange(range: models.DataStructures.RangeModel.RangeBoolean)
}
