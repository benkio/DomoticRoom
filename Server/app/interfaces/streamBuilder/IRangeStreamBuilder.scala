package interfaces.streamBuilder

import models.DataStructures.RangeModel.{RangeBooleanDBJson, RangeDBJson}
import play.api.libs.iteratee.Iteratee

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by parallels on 2/27/16.
  */
trait IRangeStreamBuilder extends IStreamBuilder{
  def buildRangeStream(implicit ec: ExecutionContext) : Future[Iteratee[RangeDBJson, List[RangeDBJson]]]
  def buildBooleanRangeStream(implicit ec: ExecutionContext) : Future[Iteratee[RangeBooleanDBJson, List[RangeBooleanDBJson]]]
}
