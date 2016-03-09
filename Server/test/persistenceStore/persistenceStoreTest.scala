package persistenceStore

import com.themillhousegroup.reactivemongo.mocks.MongoMocks
import models.DataStructures.RangeModel._
import org.specs2.mutable.Specification
import play.api.libs.json.{JsObject, Json}

/**
  * Created by benkio on 3/8/16.
  */
class persistenceStoreRangeLoaderTest extends Specification with MongoMocks {
  val mockedRanges = mockedCollection(RangeDBJsonModel.RangeDBCollectionName)(this.mockDB)
  val ranges = List[JsObject] (
    Json.obj("_id" -> Json.obj("$oid" -> "56d096de350000f4030d9ab2" ),"value" -> true,"rangeType" -> 1,"dateCreated" -> "2016-02-26T18:18:06.175Z" ),
  Json.obj("_id" -> Json.obj( "$oid" -> "56d09704350000fe030d9ab4" ),"value" -> true,"rangeType" -> 3,"dateCreated" -> "2016-02-26T18:18:44.278Z" ),
  Json.obj("_id" -> Json.obj ( "$oid" -> "56d097a035000038040d9ab5" ),"minBound" -> 0,"maxBound" -> 100,"rangeType" -> 5,"dateCreated" -> "2016-02-26T18:21:20.443Z" ),
  Json.obj("_id" -> Json.obj("$oid" -> "56d097c035000036040d9ab6"),"value" -> false,"rangeType" -> 4,"dateCreated" -> "2016-02-26T18:21:52.442Z"),
  Json.obj("_id" -> Json.obj( "$oid" -> "56d17c053200004d00359740" ),"minBound"-> 10,"maxBound"-> 50,"rangeType"-> 5,"dateCreated"->"2016-02-27T10:35:49.558Z" ),
  Json.obj("_id"->Json.obj("$oid"-> "56d17d193200008200359741" ),"minBound"->60,"maxBound"->80,"rangeType"->5,"dateCreated"->"2016-02-27T10:40:25.347Z")
  )

  givenMongoFindAnyReturns(mockedRanges, ranges)

}
