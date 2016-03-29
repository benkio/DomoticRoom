package controllers.StreamBuilder

import play.api.libs.iteratee.Iteratee

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by parallels on 2/27/16.
  */
object StreamUtils {
  def runIterateeFuture[T](iterateeFuture : Future[Iteratee[_,T]]) (implicit ec: ExecutionContext): Future[T] =
    iterateeFuture flatMap(i => i.run)
}
