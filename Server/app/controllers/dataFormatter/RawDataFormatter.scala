package controllers.dataFormatter

import interfaces.dataFormatter.IRawDataFormatter
import models.DataStructures.DataReceivedJson._
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object RawDataFormatter extends IRawDataFormatter {

}
