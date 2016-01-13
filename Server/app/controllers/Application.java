package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result analysis(){
	return ok(index.render("TODO ANALYSIS"));
    }

    public Result newRange(){
	return ok(index.render("TODO NEW RANGE"));
    }

    public Result status(){
	return ok(index.render("TODO STATUS"));
    }

}
