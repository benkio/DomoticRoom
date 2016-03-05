package dataFormatter

import controllers.dataFormatter.DBDataFormatter
import models.DataStructures.DataDBJson.{DataDBJsonModel, DataRangeViolationDBJson}
import models.DataStructures.{DataDBJson, RangeModel}
import org.joda.time.DateTime
import org.specs2.{ScalaCheck, Specification}
import play.api.data
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import reactivemongo.bson.{BSONObjectID, BSONBoolean, BSONDouble}

/**
  * Created by parallels on 3/5/16.
  */
class DBDataFormatterTest extends Specification with ScalaCheck {
  def is = s2"""

 this is DBDataFormatter specification
   where convertToBson method must convert the given Range type to BSONDocument                   $e1
   where convertToBson method must convert the given RangeBoolean type to BSONDocument            $e2
   where getFromattedStreamStep provide ad Enumeratee that convert properly the right JsValue
                                          """

  def e1 = prop((minBound : Int, maxBound: Int) => {
    val range = RangeModel.Range(minBound,maxBound,5)
    val result = DBDataFormatter.convertToBson(range)
    result.get(RangeModel.RangeDBJsonModel.minBound) must beEqualTo(Some(BSONDouble(minBound)))
    result.get(RangeModel.RangeDBJsonModel.minBound) must beEqualTo(Some(BSONDouble(minBound)))
  })
  def e2 = prop((value : Boolean, rangeType: Int) => {
    val rangeBoolean = RangeModel.RangeBoolean(value,rangeType)
    val result = DBDataFormatter.convertToBson(rangeBoolean)
    result.get(RangeModel.RangeBooleanDBJsonModel.rangeType) must beEqualTo(Some(BSONDouble(rangeType)))
    result.get(RangeModel.RangeBooleanDBJsonModel.value) must beEqualTo(Some(BSONBoolean(value)))
  })


  def e3 = prop(( sensorName : String,
                  dataType : Double,
                  value : Double) => {
    val jsonData = Json.obj(
      DataDBJson.id -> BSONObjectID.generate.toString(),
      DataDBJson.dateCreation -> DateTime.now(),
      DataDBJson.rangeViolation -> Json.toJson[Option[DataRangeViolationDBJson]](None),
      DataDBJson.sensorName -> sensorName,
      DataDBJson.dataType -> dataType,
      DataDBJson.value -> value
    )
    val stream = Enumerator(jsonData) &> DBDataFormatter.getFormatterStreamStep
  })
}
