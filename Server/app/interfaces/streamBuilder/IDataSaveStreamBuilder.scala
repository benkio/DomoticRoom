package interfaces.streamBuilder

import models.DataStructures.DataDBJson.DataDBJsonModel
import models.DataStructures.DataReceivedJson.DataReceivedJsonModel
import models.DataStructures.RangeModel.RangeDBJson
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.JsValue
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
  * Created by root on 3/13/16.
  */
trait IDataSaveStreamBuilder extends IStreamBuilder {
  def buildDataDoubleSaveStream(stream : Enumerator[JsValue]) : Future[Iteratee[BSONDocument, Unit]]
  def buildDataBooleanSaveStream(stream : Enumerator[JsValue]) : Future[Iteratee[BSONDocument, Unit]]
}
