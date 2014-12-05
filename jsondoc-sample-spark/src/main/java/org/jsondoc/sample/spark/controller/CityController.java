package org.jsondoc.sample.spark.controller;

import static spark.Spark.get;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.spark.pojo.City;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

@Api(name = "city spark controller", description = "A city controller made with spark")
public class CityController {
	
	@ApiMethod(path = "/city", description = "Gets a test city", verb = ApiVerb.GET)
	public static void registerCityRoute(final Gson gson) {
		get(new Route("/city") {
			@Override
			public Object handle(Request request, Response response) {
				return gson.toJson(new City("Spark city"));
			}
		});
	}

}
