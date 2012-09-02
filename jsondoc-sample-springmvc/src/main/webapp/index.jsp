<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>JSONDoc</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/prettify.js"></script>


<!-- Le styles -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet" >
<link href="css/prettify.css" rel="stylesheet" />
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

section {
	padding-top:60px;
}
</style>
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="../assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
</head>

<body onload="prettyPrint()">

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">JSONDoc</a>
			    <ul class="nav">
			    	<li><a href="#">Documentation</a></li>
			    	<li><a href="#examples">Examples</a></li>
			    	<li><a href="#gettingstarted">Getting started</a></li>
				    <li><a href="#downloads">Downloads</a></li>
				    <li><a href="#appendix">Appendix</a></li>
				    <li><a href="#contacts">Contacts</a></li>
				    <li><a href="/jsondoc.jsp" target="blank">Demo</a></li>
			    </ul>
			    <ul class="nav pull-right">
			    	<li><a href="mailto:fabio.maffioletti@gmail.com"><i class="icon-envelope"></i> Email me</a></li>
			    </ul>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="hero-unit" style="background-image: url('img/crisp_paper_ruffles.png');">
				<h1>JSONDoc</h1>
				<p>Easily generate documentation for your RESTful API</p>
				<p>
					<a class="btn btn-large" href="https://github.com/fabiomaffioletti/jsondoc" target="blank"> View project on Github </a>
					<a class="btn btn-large" href="/jsondoc.jsp" target="blank"> See live demo </a>
				</p>
			</div>
		</div>
		<div class="row">
			<section id="documentation">
			<h1>Step 1: annotate your code!</h1>
			<p>JSONDoc has a set of annotation that are completely <em>MVC framework agnostic</em>. Here is the list:</p>
			<table class="table table-striped table-bordered">
				<tr>
					<th style="width: 15%">Annotation</th>
					<th style="width: 10%">Level</th>
					<th>Description</th>
				</tr>
				<tr>
					<td><code>@Api</code></td>
					<td>Class</td>
					<td>
						<p>This annotation is to be used on your "service" class, for example controller classes in Spring MVC. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>name</td>
								<td>TRUE</td>
								<td>The name of the API</td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A description of what the API does</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiMethod</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your exposed methods. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>path</td>
								<td>TRUE</td>
								<td>The relative path for this method (ex. /country/get/{name})</td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A description of what the method does</td>
							<tr>
							<tr>
								<td>verb</td>
								<td>TRUE</td>
								<td>The request verb (or method), to be filled with an <code>ApiVerb</code> value (GET, POST, PUT, DELETE)</td>
							<tr>
							<tr>
								<td>produces</td>
								<td>TRUE</td>
								<td>An array of strings representing media types produced by the method, like application/json, application/xml, ...</td>
							<tr>
							<tr>
								<td>consumes</td>
								<td>TRUE</td>
								<td>An array of strings representing media types consumed by the method, like application/json, application/xml, ...</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiHeaders</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your method. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>headers</td>
								<td>TRUE</td>
								<td>An array of <code>@ApiHeader</code> annotations</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiHeader</code></td>
					<td>Annotation</td>
					<td>
						<p>This annotation is to be used inside an annotation of type <code>@ApiHeaders</code>. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>name</td>
								<td>TRUE</td>
								<td>The name of the header parameter</td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A description of what the parameter is needed for</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiParam</code></td>
					<td>Parameter</td>
					<td>
						<p>This annotation is to be used inside an annotation of type <code>@ApiParams</code>. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>name</td>
								<td>TRUE</td>
								<td>The name of the url parameter, as expected by the server</td>
							<tr>
							<tr>
								<td>description</td>
								<td>FALSE</td>
								<td>A description of what the parameter is needed for. Default is <code>""</code></td>
							<tr>
							<tr>
								<td>required</td>
								<td>FALSE</td>
								<td>Whether this parameter is required or not. Default value is <code>true</code></td>
							<tr>
							<tr>
								<td>allowedvalues</td>
								<td>FALSE</td>
								<td>An array representing the allowed values this parameter can have. Default value is <code>{}</code></td>
							<tr>
							<tr>
								<td>format</td>
								<td>FALSE</td>
								<td>The format of the parameter. For example, in case of dates object it couls be <code>yyyy-MM-dd HH:mm:ss</code> Default value is <code>""</code></td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiBodyObject</code></td>
					<td>Parameter</td>
					<td>
						<p>This annotation is to be used on your method and represents the parameter passed in the body of the requests</p>
					</td>
				</tr>
				<tr>
					<td><code>@ApiResponseObject</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your method and represents the returned value</p>
					</td>
				</tr>
				<tr>
					<td><code>@ApiErrors</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your method and represents the possible errors returned by the method. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>apierrors</td>
								<td>TRUE</td>
								<td>An array of <code>@ApiError</code> annotations</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiError</code></td>
					<td>Annotation</td>
					<td>
						<p>This annotation is to be used inside an annotation of type <code>@ApiErrors</code>. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>code</td>
								<td>TRUE</td>
								<td>The error code returned</td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A description of what the error code means</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiObject</code></td>
					<td>Class</td>
					<td>
						<p>This annotation is to be used on your object classes and represents an object used for communication between clients and server. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>name</td>
								<td>TRUE</td>
								<td>The name of the object. See also <code>@ApiBodyObject</code> and <code>@ApiResponseObject</code></td>
							<tr>
							<tr>
								<td>description</td>
								<td>FALSE</td>
								<td>A description of what the object is. Default value is <code>""</code></td>
							<tr>
							<tr>
								<td>show</td>
								<td>FALSE</td>
								<td>Whether to build the json documentation for this object or not. Default value is <code>true</code></td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiObjectField</code></td>
					<td>Field</td>
					<td>
						<p>This annotation is to be used on your objects' fields and represents a field of an object. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A drescription of what the field is</td>
							<tr>
							<tr>
								<td>format</td>
								<td>FALSE</td>
								<td>The format of the field. For example in case of date objects it could be <code>yyyy-MM-dd HH:m:ss</code></td>
							<tr>
							<tr>
								<td>allowedvalues</td>
								<td>FALSE</td>
								<td>An array representing the allowed values this field can have. Default value is <code>{}</code></td>
							<tr>
						</table>
					</td>
				</tr>
			</table>

		<h1>Step 2: generate the documentation!</h1>
		<h3>Using Spring MVC</h3>
		<p>If your web application already uses Spring MVC, then it's easy: the project <strong>jsondoc-springmvc</strong> already does it for you. The only thing to do on your side is to declare
		the jsondoc controller in your spring context.xml file:</p>
<pre class="prettyprint linenums">
&lt;mvc:annotation-driven /&gt;
&lt;context:component-scan base-package="your.base.package" /&gt;

&lt;bean id="documentationController" class="org.jsondoc.springmvc.controller.JSONDocController"&gt;
	&lt;property name="version" value="1.0" /&gt;
	&lt;property name="basePath" value="http://localhost:8080/api" /&gt;
	&lt;!-- change the base path of your web application, or better put a placeholder here and in the pom.xml and filter this file to have if replaced for free for different environments --&gt;
&lt;/bean&gt;
</pre>
<p>In this case the documentation will be generated at <code>http://localhost:8080/api/jsondoc</code>. You only have to specify the api version and the base path to give you readers a better information.</p>
 
		<h3>Starting from scratch</h3>
		<p>You can use your favourite MVC framework with JSONDoc. In this case you should only include <strong>jsondoc-core</strong> and use 
		<code>JSONDocUtils</code> static class to generate an object of kind <code>JSONDoc</code> that can be later marshalled in JSON with your preferred
		marshaller. Here is the code of the Spring MVC controller, maybe you will find it useful: </p>
<pre class="prettyprint linenums">
package org.jsondoc.springmvc.controller;

import javax.servlet.ServletContext;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/jsondoc")
public class JSONDocController {
	@Autowired
	private ServletContext servletContext;
	private String version;
	private String basePath;

	public void setVersion(String version) {
		this.version = version;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONDoc getApi() {
		return JSONDocUtils.getApiDoc(servletContext, version, basePath);
	}

}</pre>	
		
			<h1>Step 3: Display the generated documentation!</h1>
			<h3>With jsondoc-ui</h3>
			<p>You can include the <strong>jsondoc-ui</strong> project in your web application. This provides an interface to browse the generated
			documentation, it is built on Twitter Bootstrap and can be easily customized for you needs. Check out the <a href="/jsondoc.jsp" target="blank">live demo here</a>.</p>
			<h3>Build your own viewer</h3>
			<p>If you are not satisfied with the standard interface, you can use the generated JSON documentation in a fully customized interface!</p>
			</section>
			
			
			<section id="examples">
			<h1>Examples</h1>

<h3>Api</h3>
<pre class="prettyprint linenums">
package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.Api;

@Api(name="city services", description="Methods for managing cities")
// mvc framework annotations ommited
public class CityApi {</pre>

<h3>Exposed method</h3>
<pre class="prettyprint linenums">
package org.jsondoc.sample.controller;

import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;

// class declaration

@ApiMethod(
	path="/city/get/{name}", 
	verb=ApiVerb.GET, 
	description="Gets a city with the given name, provided that the name is between sydney, melbourne and perth",
	produces={MediaType.APPLICATION_JSON_VALUE},
	consumes={MediaType.APPLICATION_JSON_VALUE}
)
@ApiHeaders(headers={
	@ApiHeader(name="api_id", description="The api identifier")
})
@ApiErrors(apierrors={
	@ApiError(code="2000", description="City not found"),
	@ApiError(code="9000", description="Illegal argument")
})
// mvc framework annotations ommited
public @ApiResponseObject City getCityByName(String name) {
	// Here goes the method implementation
	return null;
}</pre>

<h3>Object to be documented</h3>
<pre class="prettyprint linenums">
package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "city")
public class City extends Location {

	@ApiObjectField(description = "The name of the city")
	private String name;

	public City(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}</pre>

<h3>Object that won't be shown but will be considered in building documentation</h3>
<pre class="prettyprint linenums">
package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="location", show=false)
public class Location {

	@ApiObjectField(description="The population of the location")
	private Integer population;
	@ApiObjectField(description="The square km of the location")
	private Integer squarekm;

	public Integer getPopulation() {
		return population;
	}

	public Integer getSquarekm() {
		return squarekm;
	}

}</pre>
		</section>


		<section id="gettingstarted">
			<h1>Getting started</h1>
			<h2>1) Download core library</h2>
			<p>Download jsondoc-core from this website or from the <strong><em>temporary</em></strong> maven repository. To do this you have to
			add two repositories to your project's pom.xml and declare the dependecy in your project</p>
<pre class="prettyprint linenums">
&lt;repositories&gt;
	&lt;repository&gt;
		&lt;id&gt;fm-repo-snapshots&lt;/id&gt;
		&lt;url&gt;https://raw.github.com/fabiomaffioletti/fm-repo/master/snapshots&lt;/url&gt;
	&lt;/repository&gt;
	
	&lt;repository&gt;
		&lt;id&gt;fm-repo-releases&lt;/id&gt;
		&lt;url&gt;https://raw.github.com/fabiomaffioletti/fm.repo/master/releases&lt;/url&gt;
	&lt;/repository&gt;
&lt;/repositories&gt;
</pre>

<pre class="prettyprint linenums">
&lt;dependency&gt;
	&lt;groupId&gt;org.jsondoc&lt;/groupId&gt;
	&lt;artifactId&gt;jsondoc-core&lt;/artifactId&gt;
	&lt;version&gt;1.0.0-SNAPSHOT&lt;/version&gt;
&lt;/dependency&gt;
</pre>

			<p>If you choose to download jars instead of using temportary repository, here is how to install them into your local repository:</p>
			<p><code>mvn install:install-file -Dfile=&lt;path-to-file&gt;jsondoc-core.jar -DgroupId=org.jsondoc -DartifactId=jsondoc-core  -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar</code></p>
			<p><code>mvn install:install-file -Dfile=&lt;path-to-file&gt;jsondoc-springmvc.jar -DgroupId=org.jsondoc -DartifactId=jsondoc-springmvc  -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar</code></p>

		<h2>2) Expose your documentation</h2>
		<p>After having annotated your code you need to create and expose your documentation. To do this you can build you own class using <code>JSONDocUtils.getApiDoc(servletContext, version, basePath);</code> or you can
		use the <code>jsondoc-springmvc</code> library. Refer to "Step 2: generate the documentation" on this page. In this case you can download the library from this website or
		from the <strong><em>temporary</em></strong> maven repository.</p>
		  
<pre class="prettyprint linenums">
&lt;dependency&gt;
	&lt;groupId&gt;org.jsondoc&lt;/groupId&gt;
	&lt;artifactId&gt;jsondoc-springmvc&lt;/artifactId&gt;
	&lt;version&gt;1.0.0-SNAPSHOT&lt;/version&gt;
&lt;/dependency&gt;
</pre>

		<h2>3) Display the docs</h2>
		<p>This is optional, but you most probably want it. If you are using the jsondoc-springmvc library, your documentation will be at <code>/jsondoc</code>. Otherwise at the
		path you defined for your generation class. You may want to display the docs in a human readable way, and fot his purpose you can use the <code>jsondoc-ui</code> project, which
		you can download from this website. Simply copy things under <code>src/main/webapps</code> to your project's webapps folder. Maybe you will need to modify some paths if you decide
		to put that stuff in a different location, but in any case everything is in the <code>&lt;head&gt;</code> of <code>jsondoc.jsp</code>. At this point you only have to 
		point your browser to <code>http://localhost:8080/jsondoc.jsp</code> (or whatever path your server has), insert the <code>/jsondoc</code> path in the upper box, like for example 
		<code>http://localhost:8080/api/jsondoc</code> and hit enter to see the documentation.</p>

		</section>
		
		<section  id="downloads">
			<h1>Downloads</h1>
			<p>
			<table class="table table-bordered table-striped">
				<tr><th>Project</th><th>Description</th></tr>
				<tr><td><a href="static/jsondoc-core.jar">jsondoc-core</a></td><td>Set of annotations. Download only this if your web application is not using Spring MVC</td></tr>
				<tr><td><a href="static/jsondoc-springmvc.jar">jsondoc-springmvc</a></td><td>Download this if you want to use the jsondoc controller to automatically generate json documentation. Until jsondoc is not on a public maven repository, you have to download and install also jsondoc-core</td></tr>
				<tr><td><a href="static/jsondoc-ui.zip">jsondoc-ui</a></td><td>Standard interface to display the generated documentation</td></tr>
			</table>
		</section>
		
		<section id="contacts">
			<h1>Contacts</h1>
			<ul class="unstyled">
				<li><i class="icon-envelope-alt"></i> <a href="mailto:fabio.maffioletti@gmail.com" target="blank">Email</a></li>
				<li><i class="icon-globe"></i> <a href="http://fabio-maffioletti.appspot.com" target="blank">Personal website</a></li>
				<li><i class="icon-linkedin"></i> <a href="http://www.linkedin.com/in/fabiomaffioletti" target="blank">Linkedin profile</a></li>
				<li><i class="icon-github"></i> <a href="https://github.com/fabiomaffioletti/" target="blank">Github repository</a></li>
			</ul>
		</section>
		
		<section id="appendix">
			<h1>Appendix: example of generated jsondoc</h1>
			<p>
<pre class="pre-scrollable prettyprint linenums">{
    "version": "1.0",
    "basePath": "http://localhost:8080/api",
    "apis": [
        {
            "jsondocId": "910b7629-944c-41c3-be0d-0c11952defea",
            "name": "city services",
            "description": "Methods for managing cities",
            "methods": [
                {
                    "jsondocId": "626c75ad-603d-45c8-a5ce-9770184bf4fe",
                    "path": "/city/get/{name}",
                    "description": "Gets a city with the given name, provided that the name is between sydney, melbourne and perth",
                    "verb": "GET",
                    "produces": [
                        "application/json"
                    ],
                    "consumes": [
                        "application/json"
                    ],
                    "headers": [
                        {
                            "jsondocId": "ee996f57-4124-4c91-b2d7-231c7aa22ab6",
                            "name": "api_id",
                            "description": "The api identifier"
                        }
                    ],
                    "urlparameters": [
                        {
                            "jsondocId": "392b54f6-c69f-4bd0-970b-2c3ae5ba5769",
                            "name": "name",
                            "description": "The city name",
                            "type": "string",
                            "required": "true",
                            "allowedvalues": [
                                "Melbourne",
                                "Sydney",
                                "Perth"
                            ],
                            "format": ""
                        },
                        {
                            "jsondocId": "5b93ac13-8d1f-4e5c-b7f5-d5689bec074a",
                            "name": "path",
                            "description": "The city path",
                            "type": "string",
                            "required": "true",
                            "allowedvalues": [],
                            "format": ""
                        }
                    ],
                    "bodyobject": null,
                    "response": {
                        "jsondocId": "0e4b1744-04b1-4553-9a80-6c7c7b934672",
                        "object": "city",
                        "multiple": "false"
                    },
                    "apierrors": [
                        {
                            "jsondocId": "b02e613c-4196-4361-ad6f-f91d26b68b35",
                            "code": "2000",
                            "description": "City not found"
                        },
                        {
                            "jsondocId": "bbed9ced-83f5-4556-b5a7-954271af1d84",
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                }
            ]
        },
        {
            "jsondocId": "b47a565c-0754-443a-801a-47cdb9d44629",
            "name": "country services",
            "description": "Methods for managing countries",
            "methods": [
                {
                    "jsondocId": "e5ec7971-9131-49da-9328-0a11b80eaf46",
                    "path": "/country/get/{name}",
                    "description": "Gets a country with the given name",
                    "verb": "GET",
                    "produces": [
                        "application/json"
                    ],
                    "consumes": [
                        "application/json"
                    ],
                    "headers": [],
                    "urlparameters": [
                        {
                            "jsondocId": "7a15b265-3a01-47b2-8b79-bb4ee7e05fc4",
                            "name": "name",
                            "description": "",
                            "type": "string",
                            "required": "true",
                            "allowedvalues": [],
                            "format": ""
                        }
                    ],
                    "bodyobject": null,
                    "response": {
                        "jsondocId": "00213ea8-1951-4ffc-8824-c6093327a4ae",
                        "object": "country",
                        "multiple": "false"
                    },
                    "apierrors": [
                        {
                            "jsondocId": "73e1b976-d039-4aa1-bf83-d94b4a7c537e",
                            "code": "1000",
                            "description": "Country not found"
                        },
                        {
                            "jsondocId": "7f408c4c-3feb-4938-9539-3bfb394e54f6",
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                },
                {
                    "jsondocId": "7d3bfba0-87e3-485e-854a-9f918aaecd99",
                    "path": "/country/all",
                    "description": "Gets all the countries",
                    "verb": "GET",
                    "produces": [
                        "application/json"
                    ],
                    "consumes": [
                        "application/json"
                    ],
                    "headers": [],
                    "urlparameters": [],
                    "bodyobject": null,
                    "response": {
                        "jsondocId": "abafe4ab-57c5-46e9-8cdc-120baf2aa13b",
                        "object": "country",
                        "multiple": "true"
                    },
                    "apierrors": []
                },
                {
                    "jsondocId": "58a5d779-dfe4-4f73-abff-b12fbf06a7fa",
                    "path": "/country/save",
                    "description": "Saves a country, with a list of cities",
                    "verb": "POST",
                    "produces": [
                        "application/json"
                    ],
                    "consumes": [
                        "application/json"
                    ],
                    "headers": [
                        {
                            "jsondocId": "ec21ad1a-2fd2-4cf4-9e54-e482d84b4856",
                            "name": "application_id",
                            "description": "The application id"
                        }
                    ],
                    "urlparameters": [],
                    "bodyobject": {
                        "jsondocId": "34916cf1-d594-46f9-a213-d543cd753250",
                        "object": "country",
                        "multiple": "false"
                    },
                    "response": {
                        "jsondocId": "9e6599a7-0ab4-4378-bfff-03a8af6dee8a",
                        "object": "string",
                        "multiple": "false"
                    },
                    "apierrors": [
                        {
                            "jsondocId": "0555c5b7-f6ae-4b66-8519-5d96e162c83f",
                            "code": "5000",
                            "description": "Duplicate country"
                        },
                        {
                            "jsondocId": "c6f0986d-b006-48b8-bd2f-bb8280d15c63",
                            "code": "6000",
                            "description": "Validation error"
                        },
                        {
                            "jsondocId": "d6222341-35ee-45a9-a299-135a93adc0df",
                            "code": "7000",
                            "description": "Invalid application id"
                        },
                        {
                            "jsondocId": "d1b23f28-d85f-4043-a224-50ef28c11ec6",
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                },
                {
                    "jsondocId": "d5539e87-f482-4815-9ea5-d3287dd73d46",
                    "path": "/country/delete/{id}",
                    "description": "Deletes the country with the given id",
                    "verb": "DELETE",
                    "produces": [
                        "application/json"
                    ],
                    "consumes": [
                        "application/json"
                    ],
                    "headers": [
                        {
                            "jsondocId": "719ea519-097a-4671-953b-fae48f37e377",
                            "name": "application_id",
                            "description": "The application id"
                        }
                    ],
                    "urlparameters": [
                        {
                            "jsondocId": "d97350da-b24c-4d73-a87d-2decd501dd2a",
                            "name": "id",
                            "description": "",
                            "type": "integer",
                            "required": "true",
                            "allowedvalues": [],
                            "format": ""
                        }
                    ],
                    "bodyobject": null,
                    "response": {
                        "jsondocId": "7e88b628-014f-40e7-bf10-c606071f1776",
                        "object": "boolean",
                        "multiple": "false"
                    },
                    "apierrors": [
                        {
                            "jsondocId": "e2eba05a-9558-4fee-a679-072802df6646",
                            "code": "1000",
                            "description": "Country not found"
                        },
                        {
                            "jsondocId": "70ac5599-ad6d-4b17-b338-9b4cf28b36a0",
                            "code": "7000",
                            "description": "Invalid application id"
                        },
                        {
                            "jsondocId": "28ca0dbd-8099-425b-8889-bd41c99fdd96",
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                }
            ]
        }
    ],
    "objects": [
        {
            "jsondocId": "11fec27a-6787-4bb5-8f3a-3af72e01fa17",
            "name": "city",
            "description": "",
            "fields": [
                {
                    "jsondocId": "21f3d2d1-c7f6-4f33-b2c7-97d6bc35f143",
                    "name": "name",
                    "type": "string",
                    "multiple": "false",
                    "description": "The name of the city",
                    "format": "",
                    "allowedvalues": []
                },
                {
                    "jsondocId": "5aabe241-ac09-4286-8d65-d66e50364699",
                    "name": "population",
                    "type": "integer",
                    "multiple": "false",
                    "description": "The population of the location",
                    "format": "",
                    "allowedvalues": []
                },
                {
                    "jsondocId": "00a41d07-6b38-470a-b587-1d13b5b7b8e9",
                    "name": "squarekm",
                    "type": "integer",
                    "multiple": "false",
                    "description": "The square km of the location",
                    "format": "",
                    "allowedvalues": []
                }
            ]
        },
        {
            "jsondocId": "c41a5420-d54c-4ed7-84d0-e3d29881c376",
            "name": "country",
            "description": "",
            "fields": [
                {
                    "jsondocId": "2d98ed4b-afca-4980-8272-961e3c0ad048",
                    "name": "name",
                    "type": "string",
                    "multiple": "false",
                    "description": "The name of the country",
                    "format": "",
                    "allowedvalues": []
                },
                {
                    "jsondocId": "771aaed2-c67b-4a98-b2df-01da78745e31",
                    "name": "cities",
                    "type": "city",
                    "multiple": "true",
                    "description": "The cities of the country",
                    "format": "",
                    "allowedvalues": []
                },
                {
                    "jsondocId": "66fb13e7-7ab3-4249-bcac-988eb74b9268",
                    "name": "population",
                    "type": "integer",
                    "multiple": "false",
                    "description": "The population of the location",
                    "format": "",
                    "allowedvalues": []
                },
                {
                    "jsondocId": "f4905325-328d-40be-9acd-269b21953949",
                    "name": "squarekm",
                    "type": "integer",
                    "multiple": "false",
                    "description": "The square km of the location",
                    "format": "",
                    "allowedvalues": []
                }
            ]
        }
    ]
}
</pre>
		</section>
			
		</div>
	</div>

</body>
</html>