package interfaces.dataAnalyzers

import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue

/**
  * Created by parallels on 1/17/16.
  */
trait IDataAnalyzer {
  def analyze(strem: Enumeratee[JsValue,JsValue]):Enumeratee[JsValue,JsValue]
}
