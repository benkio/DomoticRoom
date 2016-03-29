package controllers.StreamBuilder

import interfaces.streamBuilder.IRangeStreamBuilder
import models.DataStructures.RangeModel._
import models.persistenceStore.PersistenceStore
import play.api.libs.iteratee.{Iteratee, Enumeratee}

import scala.concurrent.ExecutionContext

/**
  * Created by parallels on 2/27/16.
  */
object FetchRangesStreamBuilder extends IRangeStreamBuilder{
  override def buildRangeStream(implicit ec: ExecutionContext)  = (PersistenceStore.loadLastRanges &>
    Enumeratee.filter(x => RangeDBBsonHandler.readTry(x).isSuccess) &>
    Enumeratee.map(y => RangeDBBsonHandler.read(y)))
    .apply(Iteratee.fold[RangeDBJson, List[RangeDBJson]](List.empty) { (s, e) => s.::(e) })

  override def buildBooleanRangeStream(implicit ec: ExecutionContext) = (PersistenceStore.loadLastRanges &>
    Enumeratee.filter(x => RangeBooleanDBBsonHandler.readTry(x).isSuccess) &>
    Enumeratee.map(y => RangeBooleanDBBsonHandler.read(y)))
    .apply(Iteratee.fold[RangeBooleanDBJson, List[RangeBooleanDBJson]](List.empty) { (s, e) => s.::(e) })
}
