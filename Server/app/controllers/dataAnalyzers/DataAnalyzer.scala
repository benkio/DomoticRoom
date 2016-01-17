package controllers.dataAnalyzers

import interfaces.dataAnalyzers.IDataAnalyzer
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/17/16.
  */
class DataAnalyzer extends IDataAnalyzer{
  override def analyze(strem: Enumeratee[JsValue, JsValue]): Enumeratee[JsValue, JsValue] = ??? //TODO
}
