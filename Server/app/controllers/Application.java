package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.Range;

//TODO: move all in a different controllers

public class Application extends Controller {

    public Result redirect(){
	return ok(redirect.render());
    }
    public Result analysis(){
	// TODO: what?
	return ok(index.render("TODO ANALYSIS"));
    }

    public Result newRange(){
	//Make the form look pretty
	Form<Range> rangeForm = Form.form(Range.class);
	return ok(newrange.render(rangeForm));
    }

    public Result submitNewRange(){
	//TODO: get data and store it.
	return ok();
    }

    public Result ranges(){
	//TODO: fetch data from database and send it back
	return ok(index.render("TODO GET RANGES"));
    }

    public Result status(){
	//TODO: add API for send data back (AJAX)
	return ok(status.render());
    }
}
