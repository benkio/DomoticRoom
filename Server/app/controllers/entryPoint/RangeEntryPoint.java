package controllers.entryPoint;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.Range;

public class RangeEntryPoint extends Controller {

	public Result newRange() {
		Form<Range> rangeForm = Form.form(Range.class);
		return ok(newrange.render(rangeForm));
	}

	public Result submitNewRange() {
		// TODO: get data and store it.
		return ok();
	}

	public Result ranges() {
		// TODO: fetch data from database and send it back
		return ok(index.render("TODO GET RANGES"));
	}

}
