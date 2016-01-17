package models.streamBuilder

import controllers.dataFormatter.RawDataFormatter
import interfaces.dataFormatter._
import interfaces.dataInput._
import interfaces.managers.IEventManager
import interfaces.presentator.IPresentator
import interfaces.presistenceStore._
import interfaces.streamBuilder.IStreamBuilder

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
class NewDataStreamBuilder(dataReceiver :IDataReceiver,
                           rawDataFormatter: IRawDataFormatter,
                           persistenceStore:IPersistenceStore,
                           eventManager:IEventManager,
                           presentator :IPresentator,
                           dbDataFormatter:IDBDataFormatter) extends IStreamBuilder{
  override def buildStream(): Unit = ??? //TODO
}
