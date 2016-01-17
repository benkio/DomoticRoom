package interfaces.presentator

import javax.swing.text.html.HTML

import play.api.libs.iteratee.Iteratee
import play.api.libs.json.JsValue

/**
  * Created by Enrico Benini (AKA Benkio) benkio89@gmail.com on 1/17/16.
  */
trait IPresentator {
  def presentData(stream :Iteratee[JsValue,JsValue]):HTML
}
