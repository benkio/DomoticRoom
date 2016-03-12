package routesAndController

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test._

/**
  * Created by root on 3/12/16.
  */
@RunWith(classOf[JUnitRunner])
class RangeEntryPointTest extends Specification {

  val app = FakeApplication()
  val server = new TestServer(application = app, port = 9000)
  step(server.start())

  "RangeEntryPoint" should {
    "where the route must be /DomoticRoom/ranges with 200" in {
      val result = route(FakeRequest(GET, "/domoticRoom/ranges")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
    }
    "where rhe route /domoticRoom/ranges must contain the ranges table" in {
      val result = route(FakeRequest(GET, "/domoticRoom/ranges")).get
      contentAsString(result) must contain("rangesTable")
    }
  }

  step(server.stop())
}
