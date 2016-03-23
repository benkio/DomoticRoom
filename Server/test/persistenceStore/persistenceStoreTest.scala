package persistenceStore

import com.themillhousegroup.reactivemongo.mocks.MongoMocks
import models.DataStructures.RangeModel
import models.DataStructures.RangeModel._
import models.persistenceStore.loaders.PersistenceStoreRangeLoader
import org.joda.time._
import org.specs2.mutable.Specification
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}
import reactivemongo.bson.BSON

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by benkio on 3/8/16.
  */
class persistenceStoreRangeLoaderTest extends Specification with MongoMocks {
  val mockedRanges = mockedCollection(RangeDBJsonModel.RangeDBCollectionName)(this.mockDB)
  val ranges = Set[JsObject] (
    Json.obj("_id" -> Json.obj("$oid" -> "56d096de350000f4030d9ab2" ),"value" -> true,"rangeType" -> 1,"dateCreated" -> "2016-02-26 18:18:06.175" ),
  Json.obj("_id" -> Json.obj( "$oid" -> "56d09704350000fe030d9ab4" ),"value" -> true,"rangeType" -> 3,"dateCreated" -> "2016-02-26 18:18:44.278" ),
  Json.obj("_id" -> Json.obj ( "$oid" -> "56d097a035000038040d9ab5" ),"minBound" -> 0,"maxBound" -> 100,"rangeType" -> 5,"dateCreated" -> "2016-02-26 18:21:20.443" ),
  Json.obj("_id" -> Json.obj("$oid" -> "56d097c035000036040d9ab6"),"value" -> false,"rangeType" -> 4,"dateCreated" -> "2016-02-26 18:21:52.442"),
  Json.obj("_id" -> Json.obj( "$oid" -> "56d17c053200004d00359740" ),"minBound"-> 10,"maxBound"-> 50,"rangeType"-> 5,"dateCreated"->"2016-02-27 10:35:49.558" ),
  Json.obj("_id"->Json.obj("$oid"-> "56d17d193200008200359741" ),"minBound"->60,"maxBound"->80,"rangeType"->5,"dateCreated"->"2016-02-27 10:40:25.347")
  )

  givenMongoFindAnyReturns(mockedRanges, ranges)

  val persistenceStoreRangeLoader = new PersistenceStoreRangeLoader(this.mockReactiveMongoApi)
  "PersistenceStoreRangeLoader" should {
    val startDate = new DateTime(2016,3,1,0,0)
    val duration = Days.days(30).toStandardDuration
   /* "loadRange Temperature in date range" in {

      val result : Future[Boolean] = Await.result(persistenceStoreRangeLoader.loadRange(RangeType.Temperature,startDate,duration)(Iteratee.fold(true)((state,x) => {
        RangeModel.validateRangeDBJson(x) match {
          case y: JsError =>
            println(x)
            println(y.errors)
            false
          case y: JsSuccess[RangeDBJson] =>
            println(x)
            val z = y.value
            state &&
              z.rangeType == rangeTypeToInt(RangeType.Temperature) &&
              z.dateCreated.isAfter(startDate) == true &&
              z.dateCreated.isBefore(startDate.plus(duration)) == true
        }})),DurationInt(30).seconds).run

        Await.result(result,30.seconds) must beTrue

    }
*/
    "load last range of Temperature" in {
      var result = Await.result(persistenceStoreRangeLoader.loadLastRange(RangeType.Temperature),30.seconds).get

      RangeModel.validateRangeDBJson(result) match {
        case x :JsError =>
          println(x.errors)
          false must beTrue
        case x : JsSuccess[RangeDBJson] =>
          val y = x.value
          y.rangeType mustEqual rangeTypeToInt(RangeType.Temperature)
          y.dateCreated.toString(RangeModel.ISO8601) must beEqualTo("2016-02-27 10:40:25.347")
      }
    }


  }
}