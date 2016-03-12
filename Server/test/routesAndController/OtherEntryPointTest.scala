package routesAndController

import controllers.entryPoint.StatusEntryPoint
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

/**
  * Created by root on 3/12/16.
  */
@RunWith(classOf[JUnitRunner])
class OtherEntryPointTest extends Specification {

  "The OtherEntryPoint" should {
    "rounts must redirect to StatusEntryPoint" in new WithApplication {
      val Some(result) = route(FakeRequest(GET, "/"))

      redirectLocation(result) must beSome.which(_ == "/domoticRoom/status")
    }
  }

}
