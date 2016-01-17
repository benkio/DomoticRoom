package models.streamBuilder

import interfaces.dataAnalyzers.IDataAnalyzer
import interfaces.dataFormatter.{IResponseDataFormatter, IRequestDataFormatter}
import interfaces.presentator.IPresentator
import interfaces.presistenceStore.IPersistenceStore
import interfaces.streamBuilder.IStreamBuilder
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class DataRequestStreamBuilder(dataAnalyzer: IDataAnalyzer,
                               requestDataFormatter:IRequestDataFormatter,
                               responseDataFormatter: IResponseDataFormatter,
                               persistenceStore:IPersistenceStore,
                               presentator:IPresentator) extends IStreamBuilder{
  override def buildStream(): Unit = ??? //TODO
  private def applyAnalysis():Iteratee[JsValue,JsValue] = ??? //TODO
}
