package controllers.entryPoint

import controllers.dataFormatter.DBDataFormatter
import controllers.managers.EventManager
import models.DataStructures
import models.DataStructures.RangeModel._
import models.persistenceStore.PersistenceStore
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
    ) (Range.apply)(Range.unapply)
  )

  // List ranges entry
  def ranges = Action { Ok(views.html.index("test")) }

  //////////////////////////////////////////
  // Form range entries
  /////////////////////////////////////////

  def newRange =  Action {
    Ok(newrange.render(userForm,messages))
  }

  def submitNewRange = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => { BadRequest(newrange.render(formWithErrors,messages))},
      userData => {

        val rangeDBDocument = new DBDataFormatter().convertToBson(userData)
        PersistenceStore.saveRange(rangeDBDocument)
        EventManager.newRange(userData)

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