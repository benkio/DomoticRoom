package interfaces.streamBuilder

import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.JsValue

import scala.concurrent.Future

/**
  * Created by root on 3/13/16.
  */
trait IDataSaveStreamBuilder extends IStreamBuilder {
  def buildDataDoubleSaveStream(stream : Enumerator[JsValue]) : Future[Iteratee[_, Unit]]
  def buildDataBooleanSaveStream(stream : Enumerator[JsValue]) : Future[Iteratee[_, Unit]]
}
