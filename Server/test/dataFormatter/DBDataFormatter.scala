package dataFormatter

import controllers.dataFormatter.DBDataFormatter
import models.DataStructures.RangeModel
import org.specs2.{ScalaCheck, Specification}
import reactivemongo.bson.{BSONBoolean, BSONDouble}

/**
  * Created by parallels on 3/5/16.
  */
class DBDataFormatterTest extends Specification with ScalaCheck {
  def is = s2"""

 this is DBDataFormatter specification
   where convertToBson method must convert the given Range type to BSONDocument                   $e1
   where convertToBson method must convert the given RangeBoolean type to BSONDocument            $e2
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
}
