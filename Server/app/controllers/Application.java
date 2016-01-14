package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.Range;

public class Application extends Controller {

    public Result analysis(){
	return ok(index.render("TODO ANALYSIS"));
    }

    public Result newRange(){
	Form<Range> rangeForm = Form.form(Range.class);
	return ok(newrange.render(rangeForm));
    }

    public Result status(){
	return ok(status.render());
    }

    public Result submitNewRange(){
	return ok();
    }
}
