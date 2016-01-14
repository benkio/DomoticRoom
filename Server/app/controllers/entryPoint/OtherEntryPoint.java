package controllers.entryPoint;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.Range;

public class OtherEntryPoint extends Controller {

	public Result redirect() {
		return ok(redirect.render());
	}

}
