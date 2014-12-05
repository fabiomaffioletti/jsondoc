package org.jsondoc.sample.spark.controller;

import static spark.Spark.get;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiVerb;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

@Api(name = "home spark controller", description = "The home page controller made with spark")
public class HomeController {

	@ApiMethod(path = "/", description = "Gets the home page", verb = ApiVerb.GET)
	public static void registerHomeRoute(final Gson gson) {
		get(new Route("/") {
			@Override
			public Object handle(Request request, Response response) {
				return gson.toJson("JSONDoc in Spark");
			}
		});
	}
	
}
