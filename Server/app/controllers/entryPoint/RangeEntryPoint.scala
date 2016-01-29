package controllers.entryPoint

import models.DataStructures
import models.DataStructures.Range
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.i18n.{Messages, Lang}
import play.api.mvc._
import views.html._

class RangeEntryPoint extends Controller {

  implicit val messages: Messages = play.api.i18n.Messages.Implicits.applicationMessages(
    Lang("en"), play.api.Play.current)

  /////////////////////////////////
  // FORMS
  /////////////////////////////////

  val userForm = Form(
    mapping(
      "minBound" -> of[Double],
      "maxBound" -> of[Double],
      "rangeType" -> number
    ) (Range.apply)(DataStructures.Range.unapply)
  )

  // List ranges entry
  def ranges = Action { Ok(views.html.index("test")) }

  //////////////////////////////////////////
  // Form range entries
  /////////////////////////////////////////

  def newRange =  Action {
    Ok(newrange.render(userForm,messages))
  }

  def submitNewRange = Action { request =>
    userForm.bindFromRequest().fold(
      formWithErrors => { BadRequest(newrange.render(formWithErrors,messages))},
      userData => {

        val

        Redirect(routes.RangeEntryPoint.ranges())
      }
    )
  }

  //////////////////////////////////////////
  // Form range boolean entries
  /////////////////////////////////////////

  def submitNewRangeBoolean = Action { request =>
    // TODO: handling another form for the range boolean
    Ok
  }

  def newRangeBoolean =  Action {

    // TODO: add another form for the range boolean
    Ok//(newrange.render(userForm,messages))
  }
}