package routesAndController

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
  * Created by root on 3/11/16.
  */
@RunWith(classOf[JUnitRunner])
class AnalysisEntryPointTest extends Specification {


  "the AnalysisEntryPoint" should {
    "where the route must be /DomoticRoomServer/Analysis with 200" in new WithApplication(FakeApplication()) {
        val result = route(FakeRequest(GET, "/domoticRoom/analysis")).get

        status(result) must equalTo(OK)
        contentType(result) must beSome.which(_ == "text/html")
        ok
    }
  }
}
