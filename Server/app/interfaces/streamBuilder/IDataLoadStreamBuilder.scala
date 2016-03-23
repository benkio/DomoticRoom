package interfaces.streamBuilder

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue

/**
  * Created by root on 3/23/16.
  */
trait IDataLoadStreamBuilder {
    def getDataStream : Enumerator[JsValue]
}
