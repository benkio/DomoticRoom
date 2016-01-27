package controllers.dataFormatter

import interfaces.dataFormatter.IRawDataFormatter
import models.DataStructures.DataReceivedJson._
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class RawDataFormatter extends IRawDataFormatter{
  override def getFormatterStreamStep = {

    val convertToModel : Enumeratee[JsValue,DataReceivedJsonModel]=
      Enumeratee.map[JsValue](in => in.validate[DataReceivedJsonModel].get)

    val ValidateJson : Enumeratee[JsValue,JsValue] =
      Enumeratee.filter(input => input.validate[DataReceivedJsonModel].isSuccess)

    ValidateJson ><> convertToModel
  }
}
