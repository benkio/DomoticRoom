package controllers.entryPoint

import controllers.dataFormatter.DBDataFormatter
import controllers.managers.EventManager
import models.DataStructures.RangeModel._
import models.persistenceStore.PersistenceStore
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.i18n.{Messages, Lang}
import play.api.libs.iteratee.{Enumeratee, Iteratee}
import play.api.mvc._
import views.html._

import scala.concurrent._
import scala.concurrent.duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

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
  def ranges = Action {
    /*
    val res =
      PersistenceStore.loadLastRanges.map(x => {
        val y : IDBRange = RangeBooleanDBBsonHandler.readTry(x) match {
          case Success(v) => v
          case Failure(e) => RangeDBBsonHandler.read(x)
        }
        y
      }).apply(Iteratee.fold[IDBRange, List[IDBRange]](List.empty) { (s, e) => s.::(e) })flatMap(i => i.run)*/
    val booleanRanges =
      (PersistenceStore.loadLastRanges &>
        Enumeratee.filter(x => RangeBooleanDBBsonHandler.readTry(x).isSuccess) &>
        Enumeratee.map(y => RangeBooleanDBBsonHandler.read(y)))
      .apply(Iteratee.fold[RangeBooleanDBJson, List[RangeBooleanDBJson]](List.empty) { (s, e) => s.::(e) }) flatMap(i => i.run)
    val ranges =
      (PersistenceStore.loadLastRanges &>
      Enumeratee.filter(x => RangeDBBsonHandler.readTry(x).isSuccess) &>
        Enumeratee.map(y => RangeDBBsonHandler.read(y)))
        .apply(Iteratee.fold[RangeDBJson, List[RangeDBJson]](List.empty) { (s, e) => s.::(e) }) flatMap(i => i.run)

    Ok(range.render(Await.result(ranges,Duration.Inf),Await.result(booleanRanges,Duration.Inf)))
  }

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
}