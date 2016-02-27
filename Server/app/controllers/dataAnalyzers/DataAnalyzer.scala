package controllers.dataAnalyzers

import interfaces.dataAnalyzers.IDataAnalyzer
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
object DataAnalyzer extends IDataAnalyzer {
  override def analyze(strem: Enumeratee[JsValue, JsValue]): Enumeratee[JsValue, JsValue] = ??? //TODO
}
