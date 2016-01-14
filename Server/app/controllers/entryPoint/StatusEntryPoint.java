package controllers.entryPoint;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.status;

public class StatusEntryPoint extends Controller {

	public Result status() {
		// TODO: add API for send data back (AJAX)
		return ok(status.render());
	}
	
}
