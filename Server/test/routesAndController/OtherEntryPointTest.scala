package routesAndController

import controllers.entryPoint.StatusEntryPoint
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{Json, JsValue}
import play.api.test.Helpers._
import play.api.test.{TestServer, FakeApplication, FakeRequest, WithApplication}

/**
  * Created by root on 3/12/16.
  */
@RunWith(classOf[JUnitRunner])
class OtherEntryPointTest extends Specification {

  val app = FakeApplication()
  val server = new TestServer(application = app, port = 9000)
  step(server.start())

  "The OtherEntryPoint" should {
    "route / must redirect to StatusEntryPoint" in {
      val Some(result) = route(FakeRequest(GET, "/"))
      redirectLocation(result) must beSome.which(_ == "/domoticRoom/status")
      status(result) must equalTo(303)
    }
    "route echo must return the body" in {
      val body : JsValue = Json.obj( "testjson" -> "test" )
      val Some(result) = route(FakeRequest(POST, "/echo").withJsonBody(body))
      //
      contentAsJson(result) mustEqual(body)
    }
  }
  step(server.stop())
}
