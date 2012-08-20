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
					<td><code>@ApiParams</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your method and represents the parameters passed in the URL of the requests. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>urlparameters</td>
								<td>TRUE</td>
								<td>An array of <code>@ApiParam</code> annotations</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiParam</code></td>
					<td>Annotation</td>
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
								<td>TRUE</td>
								<td>A description of what the parameter is needed for</td>
							<tr>
							<tr>
								<td>type</td>
								<td>TRUE</td>
								<td>The type of the parameter (ex. integer, string, ...)</td>
							<tr>
							<tr>
								<td>required</td>
								<td>FALSE</td>
								<td>Whether this parameter is required or not. Default value is <code>true</code></td>
							<tr>
							<tr>
								<td>allowedvalues</td>
								<td>FALSE</td>
								<td>An array representing the allowed values this parameter can have. Default value is <code>*</code></td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiBodyObject</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your method and represents the parameter passed in the body of the requests. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>object</td>
								<td>TRUE</td>
								<td>The name of the object passed in the request body. See also <code>@ApiObject</code></td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A description of what the parameter is needed for</td>
							<tr>
							<tr>
								<td>multiple</td>
								<td>TRUE</td>
								<td>Whether it is a list of objects or a single object</td>
							<tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><code>@ApiResponseObject</code></td>
					<td>Method</td>
					<td>
						<p>This annotation is to be used on your method and represents the returned value. Here are its attributes: </p>
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Required</th>
								<th>Description</th>
							<tr>
							<tr>
								<td>object</td>
								<td>TRUE</td>
								<td>The name of the object returned by the method. See also <code>@ApiObject</code></td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A description of what the object contains or represents</td>
							<tr>
							<tr>
								<td>multiple</td>
								<td>TRUE</td>
								<td>Whether it is a list of objects or a single object</td>
							<tr>
						</table>
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
								<td>The name of the object, to be referenced by other annotations with an "object" attribute. See also <code>@ApiBodyObject</code> and <code>@ApiResponseObject</code></td>
							<tr>
							<tr>
								<td>extendsclass</td>
								<td>FALSE</td>
								<td>Fully qualified path of the parent class. This has to be specified if your class extends another one and you want to display the object structure in a single shot. Default value is <code>""</code></td>
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
								<td>name</td>
								<td>TRUE</td>
								<td>The name of the field</td>
							<tr>
							<tr>
								<td>description</td>
								<td>TRUE</td>
								<td>A drescription of what the field is</td>
							<tr>
							<tr>
								<td>type</td>
								<td>TRUE</td>
								<td>The type of the field. This can be a simple type like integer, string, ... or can be an object. See also <code>@ApiObject</code></td>
							<tr>
							<tr>
								<td>multiple</td>
								<td>TRUE</td>
								<td>Whether this field is a list or not</td>
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
@ApiParams(urlparameters={
	@ApiParam(name="name", required = true, description="name description", type = "string", allowedvalues = {"sydney", "melbourne", "perth"})
})
@ApiResponseObject(
	object="city", 
	description="A city object", 
	multiple = true)
@ApiErrors(apierrors={
	@ApiError(code="2000", description="City not found"),
	@ApiError(code="9000", description="Illegal argument")
})
// mvc framework annotations ommited
public City getCityByName(String name) {
	// Here goes the method implementation
	return null;
}</pre>

<h3>Object to be documented</h3>
<pre class="prettyprint linenums">
package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "city", extendsclass="org.jsondoc.sample.pojo.Location")
public class City extends Location {

	@ApiObjectField(name = "name", description = "The name of the city", type = "string", multiple = false)
	private String name;

	public City(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}</pre>

<h3>Object to be ignored when building documentation</h3>
<pre class="prettyprint linenums">
package org.jsondoc.sample.pojo;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name="location", show=false)
public class Location {

	@ApiObjectField(name="population", multiple=false, description="The population of the location", type="integer")
	private Integer population;
	@ApiObjectField(name="squarekm", multiple=false, description="The square km of the location", type="integer")
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
            "name": "city services",
            "description": "Methods for managing cities",
            "methods": [
                {
                    "path": "/city/get/{name}",
                    "description": "Gets a city with the given name, provided that the name is between sydney, melbourne and perth",
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
                            "name": "name",
                            "description": "name description",
                            "type": "string",
                            "required": true,
                            "allowedvalues": [
                                "sydney",
                                "melbourne",
                                "perth"
                            ]
                        }
                    ],
                    "bodyobject": null,
                    "response": {
                        "object": "city",
                        "description": "A city object",
                        "multiple": true
                    },
                    "apierrors": [
                        {
                            "code": "2000",
                            "description": "City not found"
                        },
                        {
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                }
            ]
        },
        {
            "name": "country services",
            "description": "Methods for managing countries",
            "methods": [
                {
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
                            "name": "name",
                            "description": "The country name",
                            "type": "string",
                            "required": true,
                            "allowedvalues": [
                                "*"
                            ]
                        }
                    ],
                    "bodyobject": null,
                    "response": {
                        "object": "country",
                        "description": "A country object, with the list of its cities",
                        "multiple": false
                    },
                    "apierrors": [
                        {
                            "code": "1000",
                            "description": "Country not found"
                        },
                        {
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                },
                {
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
                        "object": "country",
                        "description": "A list of country objects",
                        "multiple": true
                    },
                    "apierrors": []
                },
                {
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
                            "name": "application_id",
                            "description": "The application id"
                        }
                    ],
                    "urlparameters": [],
                    "bodyobject": {
                        "object": "country",
                        "description": "A country object, with the list of its cities",
                        "multiple": false
                    },
                    "response": {
                        "object": "country",
                        "description": "The saved country, with its generated id",
                        "multiple": false
                    },
                    "apierrors": [
                        {
                            "code": "5000",
                            "description": "Duplicate country"
                        },
                        {
                            "code": "6000",
                            "description": "Validation error"
                        },
                        {
                            "code": "7000",
                            "description": "Invalid application id"
                        },
                        {
                            "code": "9000",
                            "description": "Illegal argument"
                        }
                    ]
                },
                {
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
                            "name": "application_id",
                            "description": "The application id"
                        }
                    ],
                    "urlparameters": [
                        {
                            "name": "id",
                            "description": "The country id",
                            "type": "integer",
                            "required": true,
                            "allowedvalues": [
                                "*"
                            ]
                        }
                    ],
                    "bodyobject": null,
                    "response": {
                        "object": "boolean",
                        "description": "A boolean representing the result of the operation",
                        "multiple": false
                    },
                    "apierrors": [
                        {
                            "code": "1000",
                            "description": "Country not found"
                        },
                        {
                            "code": "7000",
                            "description": "Invalid application id"
                        },
                        {
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
            "name": "city",
            "fields": [
                {
                    "name": "name",
                    "type": "string",
                    "multiple": false,
                    "description": "The name of the city"
                },
                {
                    "name": "population",
                    "type": "integer",
                    "multiple": false,
                    "description": "The population of the location"
                },
                {
                    "name": "squarekm",
                    "type": "integer",
                    "multiple": false,
                    "description": "The square km of the location"
                }
            ]
        },
        {
            "name": "country",
            "fields": [
                {
                    "name": "name",
                    "type": "string",
                    "multiple": false,
                    "description": "The name of the country"
                },
                {
                    "name": "cities",
                    "type": "city",
                    "multiple": true,
                    "description": "The cities of the country"
                },
                {
                    "name": "population",
                    "type": "integer",
                    "multiple": false,
                    "description": "The population of the location"
                },
                {
                    "name": "squarekm",
                    "type": "integer",
                    "multiple": false,
                    "description": "The square km of the location"
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