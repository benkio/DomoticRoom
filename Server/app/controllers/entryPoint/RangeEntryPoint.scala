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

  val rangeForm = Form(
    mapping(
      "minBound" -> of[Double],
      "maxBound" -> of[Double],
      "rangeType" -> number
    ) (Range.apply)(Range.unapply)
  )

  val rangeBooleanForm = Form(
    mapping(
      "value" -> of[Boolean],
      "rangeType" -> number
    ) (RangeBoolean.apply)(RangeBoolean.unapply)
  )

  // List ranges entry
  def ranges = Action { Ok(views.html.index("test")) }

  //////////////////////////////////////////
  // Form range entries
  /////////////////////////////////////////

  def newRange =  Action {
    Ok(newrange.render(rangeForm,rangeBooleanForm,messages))
  }

  def submitNewRange = Action { implicit request =>
    rangeForm.bindFromRequest.fold(
      formWithErrors => { BadRequest(newrange.render(formWithErrors,rangeBooleanForm,messages))},
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

  def submitNewRangeBoolean = Action { implicit request =>
    rangeBooleanForm.bindFromRequest.fold(
      formWithErrors => { BadRequest(newrange.render(rangeForm,formWithErrors,messages))},
      userData => {

        val rangeDBDocument = new DBDataFormatter().convertToBson(userData)
        PersistenceStore.saveRange(rangeDBDocument)
        EventManager.newRange(userData)

        Redirect(routes.RangeEntryPoint.ranges())
      }
    )
  }

  def newRangeBoolean =  Action {

    // TODO: add another form for the range boolean
    Ok//(newrange.render(userForm,messages))
  }
}