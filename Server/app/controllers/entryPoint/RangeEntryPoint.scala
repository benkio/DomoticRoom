package controllers.entryPoint

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.i18n.{Messages, Lang}
import play.api.mvc._
import views.html._

class RangeEntryPoint extends Controller {

  implicit val messages: Messages = play.api.i18n.Messages.Implicits.applicationMessages(
    Lang("en"), play.api.Play.current)

  val userForm = Form(
    mapping(
      "minBound" -> of[Double],
      "maxBound" -> of[Double],
      "rangeType" -> number
    ) (models.Range.apply)(models.Range.unapply)
  )


  def newRange =  Action {

    Ok(newrange.render(userForm,messages))
  }
  def ranges = Action { Ok(views.html.index("test")) }
  def submitNewRange = Action { Ok }
}