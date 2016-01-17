package interfaces.managers

import play.api.libs.iteratee.Iteratee

/**
  * Created by parallels on 1/17/16.
  */
trait IConfigurator {
  def setUpSystem()
  def setUPStreams()
}
