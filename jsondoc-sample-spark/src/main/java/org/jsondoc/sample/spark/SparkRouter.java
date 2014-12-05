package org.jsondoc.sample.spark;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.sample.spark.controller.CityController;
import org.jsondoc.sample.spark.controller.HomeController;
import org.jsondoc.sample.spark.jsondoc.JSONDocController;

import com.google.gson.Gson;

public class SparkRouter {
	
	private static String version;
	private static String basePath;
	private static List<String> packages;
	private static Gson gson;
	
	public static void main(String[] args) {
		version = "1.0";
		basePath = "http://localhost:4567";
		packages = new ArrayList<String>();
		packages.add("org.jsondoc.sample.spark");
		gson = new Gson();
		
		HomeController.registerHomeRoute(gson);
		CityController.registerCityRoute(gson);
		
		JSONDocController.registerJSONDocRoute(version, basePath, packages, gson);
	}

}
