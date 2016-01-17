package models.streamBuilder

import interfaces.dataFormatter.IDBDataFormatter
import interfaces.dataInput.IDataReceiver
import interfaces.managers.IEventManager
import interfaces.presentator.IPresentator
import interfaces.presistenceStore.IPersistenceStore
import interfaces.streamBuilder.IStreamBuilder

/**
  * Created by parallels on 1/17/16.
  */
class NewRangeStreamBuilder(dataReceiver :IDataReceiver,
                            persistenceStore:IPersistenceStore,
                            eventManager:IEventManager,
                            presentator :IPresentator,
                            dbDataFormatter:IDBDataFormatter) extends IStreamBuilder {
  override def buildStream(): Unit = ??? //TODO
}
