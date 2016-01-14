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

    public Result status(){
	//TODO: add API for send data back (AJAX)
	return ok(status.render());
    }
}
