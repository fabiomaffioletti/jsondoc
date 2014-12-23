<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>JSONDoc</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="js/jlinq.js"></script>
<script type="text/javascript" src="js/prettify.js"></script>

<!-- Le styles -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.GET {
	padding-top : 5px;
	background-color: #468847;
}
.POST {
	padding-top : 5px;
	background-color: #3A87AD;
}
.PUT {
	padding-top : 5px;
	background-color: #F89406;
}
.DELETE {
	padding-top : 5px;
	background-color: #B94A48;
}

blockquote small:before {
    content: "";
}

.playground-spacer {
	margin-top: 15px;
}

.com { color: #93a1a1; }
.lit { color: #195f91; }
.pun, .opn, .clo { color: #93a1a1; }
.fun { color: #dc322f; }
.str, .atv { color: #D14; }
.kwd, .prettyprint .tag { color: #1e347b; }
.typ, .atn, .dec, .var { color: teal; }
.pln { color: #48484c; }

.prettyprint {
  padding: 8px;
  background-color: #f7f7f9;
  border: 1px solid #e1e1e8;
}
.prettyprint.linenums {
  -webkit-box-shadow: inset 40px 0 0 #fbfbfc, inset 41px 0 0 #ececf0;
     -moz-box-shadow: inset 40px 0 0 #fbfbfc, inset 41px 0 0 #ececf0;
          box-shadow: inset 40px 0 0 #fbfbfc, inset 41px 0 0 #ececf0;
}

/* Specify class=linenums on a pre to get line numbering */
ol.linenums {
  margin: 0 0 0 33px; /* IE indents via margin-left */
}
ol.linenums li {
  padding-left: 12px;
  color: #bebec5;
  line-height: 20px;
  text-shadow: 0 1px 0 #fff;
}

table td {
	word-wrap: break-word;
}
</style>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
		    	<a class="navbar-brand" href="#">JSONDoc</a>
    		</div>
    		<form class="navbar-form navbar-left col-md-8" role="search">
				<div class="form-group">
		        	<input id="jsondocfetch" type="text" class="form-control" style="width:350px" placeholder="Insert here the JSONDoc URL" value="${jsondoc.path}" autocomplete="off">
		        </div>
		        <button id="getDocButton" class="btn btn-default">Get documentation</button>
		    </form>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
				<div id="maindiv" style="display:none;"></div>
				<div id="apidiv" style="display:none;"></div>
				<div id="objectdiv" style="display:none;"></div>
			</div>

			<div class="col-md-5">
				<div id="content"></div>			
			</div>
			
			<div class="col-md-4">
				<div id="testContent"></div>			
			</div>
		</div>
	</div>

<script id="main" type="text/x-handlebars-template">
<blockquote>
  <p style="text-transform: uppercase;">API info</span></p>
  <small>Base path: {{basePath}}</small>
  <small>Version: {{version}}</small>
</blockquote>
</script>

<script id="apis" type="text/x-handlebars-template">
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">API</h3>
	</div>
	<div class="panel-body">
	{{#eachInMap apis}}
	<ul class="list-unstyled">
		{{#if key}}
		<li style="text-transform: uppercase;">{{key}}</li>
		{{/if}}
		{{#each value}}
			<li><a href="#" id="{{jsondocId}}" rel="api">{{name}}</a></li>
		{{/each}}
	</ul>
	{{/eachInMap}}	
	</div>
</div>
</script>

<script id="objects" type="text/x-handlebars-template">
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Objects</h3>
	</div>
	<div class="panel-body">
	{{#eachInMap objects}}
	<ul class="list-unstyled">
		{{#if key}}
		<li style="text-transform: uppercase;">{{key}}</li>
		{{/if}}
		{{#each value}}
			<li><a href="#" id="{{jsondocId}}" rel="object">{{name}}</a></li>
		{{/each}}
	</ul>
	{{/eachInMap}}
	</div>
</div>
</script>

<script id="methods" type="text/x-handlebars-template">
<blockquote>
  <p style="text-transform: uppercase;"><span id="apiName"></span></p>
  <small><span id="apiDescription"></span></small>
  <small><span id="apiSupportedVersions"></span></small>
</blockquote>

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	{{#methods}}
	<div class="panel panel-default">
		<div class="panel-heading" id="{{jsondocId}}">
			<h4 class="panel-title">
				<span class="label pull-right {{verb}}">{{verb}}</span>
				<a id="{{jsondocId}}" href="#_{{jsondocId}}" rel="method" data-toggle="collapse" data-parent="#accordion" aria-controls="_{{jsondocId}}" aria-expanded="true">{{path}}</a>
			</h4>
		</div>
		<div id="_{{jsondocId}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="{{jsondocId}}">
			<div class="panel-body">
				<table class="table table-condensed table-bordered" style="table-layout: fixed;">
					<tr>
						<th style="width:18%;">Path</th>
						<td><code>{{path}}</code></td>
					</tr>
					{{#if supportedversions}}
						<tr>
							<td>Since version</td>
							<td>{{supportedversions.since}}</td>
						</tr>
						{{#if supportedversions.until}}
							<tr>
								<td>Until version</td>
								<td>{{supportedversions.until}}</td>
							</tr>
						{{/if}}	
					{{/if}}
					<tr>
						<th>Description</th>
						<td>{{description}}</td>
					</tr>

					{{#if auth}}
						<tr>
							<th>Auth</th>
							<td>{{auth.type}}, Roles: {{auth.roles}}</td>
						</tr>
					{{/if}}

					{{#if produces}}
						<tr>
							<th colspan=2>Produces</th>
						</tr>
						<tr>
							<td colspan=2>
								{{#each produces}} <code>{{this}}</code> {{/each}}
							</td>
						</tr>
					{{/if}}
					{{#if consumes}}
						<tr>
							<th colspan=2>Consumes</th>
						</tr>
						<tr>
							<td colspan=2>
								{{#each consumes}} <code>{{this}}</code> {{/each}}
							</td>
						</tr>
					{{/if}}
					{{#if headers}}
						<tr>
							<th colspan=2>Headers</th>
						</tr>
						{{#each headers}}
							<tr>
								<td><code>{{this.name}}</code></td>
								<td>{{this.description}}</td>
							</tr>
						{{/each}}
					{{/if}}
					{{#if pathparameters}}
						<tr>
							<th colspan=2>Path parameters</th>
						</tr>
						{{#each pathparameters}}
							<tr>
								<td><code>{{this.name}}</code></td>
								<td>Required: {{this.required}}</td>
							</tr>
							{{#if this.description}}
							<tr>
								<td></td>
								<td>Description: {{this.description}}</td>
							</tr>
							{{/if}}
							<tr>
								<td></td>
								<td>Type: <code>{{this.jsondocType.oneLineText}}</code></td>
							</tr>
							{{#if this.allowedvalues}}
							<tr>
								<td></td>
								<td>Allowed values: {{this.allowedvalues}}</td>
							</tr>
							{{/if}}
							{{#if this.format}}
							<tr>
								<td></td>
								<td>Format: {{this.format}}</td>
							</tr>
							{{/if}}
						{{/each}}
					{{/if}}
					{{#if queryparameters}}
						<tr>
							<th colspan=2>Query parameters</th>
						</tr>
						{{#each queryparameters}}
							<tr>
								<td><code>{{this.name}}</code></td>
								<td>Required: {{this.required}}</td>
							</tr>
							{{#if this.description}}
							<tr>
								<td></td>
								<td>Description: {{this.description}}</td>
							</tr>
							{{/if}}
							<tr>
								<td></td>
								<td>Type: <code>{{this.jsondocType.oneLineText}}</code></td>
							</tr>
							{{#if this.allowedvalues}}
							<tr>
								<td></td>
								<td>Allowed values: {{this.allowedvalues}}</td>
							</tr>
							{{/if}}
							{{#if this.format}}
							<tr>
								<td></td>
								<td>Format: {{this.format}}</td>
							</tr>
							{{/if}}
						{{/each}}
					{{/if}}
					{{#if bodyobject}}
						<tr>
							<th colspan=2>Body object</th>
						</tr>
						<tr>
							<td colspan=2><code>{{bodyobject.jsondocType.oneLineText}}</code></td>
						</tr>
					{{/if}}
					{{#if response}}
						<tr>
							<th colspan=2>Response object</th>
						</tr>
						<tr>
							<td colspan=2><code>{{response.jsondocType.oneLineText}}</code></td>
						</tr>
					{{/if}}
					{{#if apierrors}}
						<tr>
							<th colspan=2>Errors</th>
						</tr>
						{{#each apierrors}}
							<tr>
								<td><code>{{this.code}}</code></td>
								<td>{{this.description}}</td>
							</tr>
						{{/each}}
					{{/if}}
				</table>
			</div>
		</div>
	</div>
	{{/methods}}
</div>
</script>

<script id="test" type="text/x-handlebars-template">
<blockquote>
  <p style="text-transform: uppercase;">Playground</span></p>
  <small>{{path}}</small>
</blockquote>

<div class="row">
	{{#if auth}}
		{{#equal auth.type "BASIC_AUTH"}}
			<div class="col-md-12">
				<h4>Basic Authentication</h4>
				<div class="form-group">
					<select class="form-control" id="basicAuthSelect" onchange="fillBasicAuthFields(); return false;">
						<option disabled="disabled" selected="selected">Select a test user or fill inputs below</option>
						{{#eachInMap auth.testusers}}
							<option value="{{value}}">{{key}}</option>
						{{/eachInMap}}
						<option value="a-wrong-password">invalidate-credentials-cache-user</option>
					</select>
				</div>
				<div class="form-group" style="margin-bottom:5px;">
					<label for="basicAuthUsername">Username</label>
					<input class="form-control" type="text" id="basicAuthUsername" name="basicAuthUsername" placeholder="Username">
				</div>
				<div class="form-group">
					<label for="basicAuthPassword">Password</label>
					<input class="form-control" type="text" id="basicAuthPassword" name="basicAuthPassword" placeholder="Password">
				</div>
			</div>
		{{/equal}}
	{{/if}}

	{{#if headers}}
	<div class="col-md-12">
		<div id="headers">
			<h4>Headers</h4>
			{{#headers}}
				<div class="form-group">
					<label for="i_{{name}}">{{name}}</label>
					<input type="text" class="form-control" name="{{name}}" placeholder="{{name}}">
				</div>
			{{/headers}}
		</div>
	</div>
	{{/if}}

	{{#if produces}}
		<div class="col-md-6" style="margin-left:0px">		
		<div id="produces" class="playground-spacer">
		<h4>Accept</h4>	
		{{#produces}}
			<label><input type="radio" name="produces" value="{{this}}"> {{this}}</label><br/>
		{{/produces}}
		</div>
		</div>
	{{/if}}

	{{#if bodyobject}}
	{{#if consumes}}
		<div class="col-md-6" style="margin-left:0px">		
		<div id="consumes" class="playground-spacer">
		<h4>Content type</h4>	
		{{#consumes}}
			<label><input type="radio" name="consumes" value="{{this}}"> {{this}}</label>
		{{/consumes}}
		</div>
		</div>
	{{/if}}
	{{/if}}

	{{#if pathparameters}}
	<div class="col-md-12">
		<div id="pathparameters" class="playground-spacer">
			<h4>Path parameters</h4>
			{{#pathparameters}}
				<div class="form-group">
					<label for="i_{{name}}">{{name}}</label>
					<input type="text" class="form-control" id="i_{{name}}" name="{{name}}" placeholder="{{name}}">
				</div>
			{{/pathparameters}}
		</div>
	</div>
	{{/if}}

	{{#if queryparameters}}
	<div class="col-md-12">
		<div id="queryparameters" class="playground-spacer">
			<h4>Query parameters</h4>
			{{#queryparameters}}
				<div class="form-group">
					<label for="i_{{name}}">{{name}}</label>
					<input type="text" class="form-control" id="i_{{name}}" name="{{name}}" placeholder="{{name}}">
				</div>
			{{/queryparameters}}
		</div>
	</div>
	{{/if}}

	{{#if bodyobject}}
	<div class="col-md-12">
		<div id="bodyobject" class="playground-spacer">
			<h4>Body object</h4>
			<textarea class="form-control" id="inputJson" rows=10 />
		</div>
	</div>
	{{/if}}

	<div class="col-md-12 playground-spacer">
		<button class="btn btn-primary" id="testButton" data-loading-text="Loading...">Submit</button>
	</div>
</div>
</div>

<div class="row">
<div class="col-md-12">
<div class="tabbable" id="resInfo" style="display:none; margin-top: 20px;">
	<ul class="nav nav-tabs">
  		<li class="active"><a href="#tab1" data-toggle="tab">Response text</a></li>
  		<li><a href="#tab2" data-toggle="tab">Response info</a></li>
  		<li><a href="#tab3" data-toggle="tab">Request info</a></li>
	</ul>
	<div class="tab-content" style="margin-top: 20px">
    	<div class="tab-pane active" id="tab1">
    		<pre id="response" class="prettyprint">
			</pre>
   		</div>
    	<div class="tab-pane" id="tab2">
			<h5 style="padding:0px">Response code</p>
      		<pre id="responseStatus" class="prettyprint">
			</pre>
			<h5 style="padding:0px">Response headers</p>
      		<pre id="responseHeaders" class="prettyprint">
			</pre>
    	</div>
		<div class="tab-pane" id="tab3">
      		<h5 style="padding:0px">Request URL</p>
      		<pre id="requestURL" class="prettyprint">
			</pre>
    	</div>
	</div>
</div>
</div>

</script>

<script id="object" type="text/x-handlebars-template">
<table class="table table-condensed table-striped table-bordered" style="table-layout: fixed;">
	<tr><th style="width:18%;">Name</th><td><code>{{name}}</code></td></tr>
	{{#if description}}
		<tr><th>Description</th><td>{{description}}</td></tr>
	{{/if}}
	{{#if supportedversions}}
		<tr>
			<td>Since version</td>
			<td>{{supportedversions.since}}</td>
		</tr>
		{{#if supportedversions.until}}
			<tr>
				<td>Until version</td>
				<td>{{supportedversions.until}}</td>	
			</tr>
		{{/if}}	
	{{/if}}
	{{#if allowedvalues}}
		<tr><td></td><td>Allowed values: {{allowedvalues}}</td></tr>
	{{/if}}
	{{#if fields}}
	<tr><th colspan=2>Fields</th></tr>
		{{#each fields}}
			<tr><td><code>{{name}}</code></td><td>{{description}}</td></tr>
			<tr><td></td><td>Required: {{required}}</td></tr>
			<tr><td></td><td>Type: <code>{{jsondocType.oneLineText}}</code></td></tr>
			{{#if format}}
				<tr><td></td><td>Format: {{format}}</td></tr>
			{{/if}}
			{{#if allowedvalues}}
				<tr><td></td><td>Allowed values: {{allowedvalues}}</td></tr>
			{{/if}}
			{{#if supportedversions}}
				<tr><td></td><td>Since version {{supportedversions.since}}</td></tr>
				{{#if supportedversions.until}}
					<tr><td></td><td>Until version {{supportedversions.until}}</td></tr>
				{{/if}}
			{{/if}}
		{{/each}}
	{{/if}}
</table>
</script>

<script>
	var model;

	Handlebars.registerHelper('equal', function(lvalue, rvalue, options) {
		if(lvalue!=rvalue) {
			return options.inverse(this);
		} else {
			return options.fn(this);
		}
	});

	Handlebars.registerHelper('eachInMap', function ( map, block ) {
		var out = '';
		Object.keys( map ).map(function( prop ) {
			out += block.fn( {key: prop, value: map[ prop ]} );
		});
		return out;
	} );
	
	function replacer(key, value) {
	    if (value == null) return undefined;
	    else return value;
	}
	
	Handlebars.registerHelper('json', function(context) {
	    return JSON.stringify(context, replacer, 2);
	});
	
	function checkURLExistence() {
		var value = $("#jsondocfetch").val();
		if(value.trim() == '') {
			alert("Please insert a valid URL");
			return false;
		} else {
			return fetchdoc(value);
		}
	}
	
	$("#jsondocfetch").keypress(function(event) {
		if (event.which == 13) {
			checkURLExistence();
			return false;
		}
	});
	
	$("#getDocButton").click(function() {
		checkURLExistence();
		return false;
	});

	function fillBasicAuthFields() {
		$("#basicAuthPassword").val($("#basicAuthSelect").val());
		$("#basicAuthUsername").val($("#basicAuthSelect").find(":selected").text());
	}
	
	function printResponse(data, res, url) {
		if(res.responseXML != null) {
			$("#response").text(formatXML(res.responseText));
		} else {
			$("#response").text(JSON.stringify(data, undefined, 2));
		}
		
		prettyPrint();
		$("#responseStatus").text(res.status);
		$("#responseHeaders").text(res.getAllResponseHeaders());
		$("#requestURL").text(url);
		$('#testButton').button('reset');
		$("#resInfo").show();
	}
	
	function formatXML(xml) {
	    var formatted = '';
	    var reg = /(>)(<)(\/*)/g;
	    xml = xml.replace(reg, '$1\r\n$2$3');
	    var pad = 0;
	    jQuery.each(xml.split('\r\n'), function(index, node) {
	        var indent = 0;
	        if (node.match( /.+<\/\w[^>]*>$/ )) {
	            indent = 0;
	        } else if (node.match( /^<\/\w/ )) {
	            if (pad != 0) {
	                pad -= 1;
	            }
	        } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
	            indent = 1;
	        } else {
	            indent = 0;
	        }

	        var padding = '';
	        for (var i = 0; i < pad; i++) {
	            padding += '  ';
	        }

	        formatted += padding + node + '\r\n';
	        pad += indent;
	    });

	    return formatted;
	}
	
	function fetchdoc(jsondocurl) {
		$.ajax({
			url : jsondocurl,
			type: 'GET',
			dataType: 'json',
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				model = data;
				var main = Handlebars.compile($("#main").html());
				var mainHTML = main(data);
				$("#maindiv").html(mainHTML);
				$("#maindiv").show();
				
				var apis = Handlebars.compile($("#apis").html());
				var apisHTML = apis(data);
				$("#apidiv").html(apisHTML);
				$("#apidiv").show();
				
				// this builds an plain array out of the apis map, that makes selecting with jlinq much easier
				var plainApis = [];
				$.each(data.apis, function(i, v) {
					$.each(v, function(j, p) {
						plainApis.push(p);	
					});
				});
				
				$("#apidiv a").each(function() {
					$(this).click(function() {
						var api = jlinq.from(plainApis).equals("jsondocId", this.id).first();
						var methods = Handlebars.compile($("#methods").html());
						var methodsHTML = methods(api);
						$("#content").html(methodsHTML);
						$("#content").show();
						$("#apiName").text(api.name);
						$("#apiDescription").text(api.description);
						if(api.supportedversions) {
							$("#apiSupportedVersions").text("Since version: " + api.supportedversions.since);
							if(api.supportedversions.until) {
								$("#apiSupportedVersions").text($("#apiSupportedVersions").text() + " - Until version: " + api.supportedversions.until);
							}
						}
						$("#testContent").hide();
						
						$('#content a[rel="method"]').each(function() {
							$(this).click(function() {
								var method = jlinq.from(api.methods).equals("jsondocId", this.id).first();
								var test = Handlebars.compile($("#test").html());
								var testHTML = test(method);
								$("#testContent").html(testHTML);
								$("#testContent").show();
								
								$("#produces input:first").attr("checked", "checked");
								
								$("#testButton").click(function() {
									var headers = new Object();
									$("#headers input").each(function() {
										headers[this.name] = $(this).val();
									});
									
									headers["Accept"] = $("#produces input:checked").val();

									if(method.auth) {
										if(method.auth.type == "BASIC_AUTH") {
											headers["Authorization"] = "Basic " + window.btoa($('#basicAuthUsername').val() + ":" + $('#basicAuthPassword').val());
										}
									}
									
									var replacedPath = method.path;
									var tempReplacedPath = replacedPath; // this is to handle more than one parameter on the url
									$("#pathparameters input").each(function() {
										tempReplacedPath = replacedPath.replace("{"+this.name+"}", $(this).val());
										replacedPath = tempReplacedPath;
									});

									$("#queryparameters input").each(function() {
										tempReplacedPath = replacedPath.replace("{"+this.name+"}", $(this).val());
										replacedPath = tempReplacedPath;
									});
									
									$('#testButton').button('loading');
									
									var res = $.ajax({
										url : model.basePath + replacedPath,
										type: method.verb,
										data: $("#inputJson").val(),
										headers: headers,
										contentType: $("#consumes input:checked").val(),
										success : function(data) {
											printResponse(data, res, this.url);
										},
										error: function(data) {
											printResponse(data, res, this.url);
										}
									});
									
								});
								
							});
						});
					});
				});
				
				var objects = Handlebars.compile($("#objects").html());
				var objectsHTML = objects(data);
				$("#objectdiv").html(objectsHTML);
				$("#objectdiv").show();
				
				// this builds an plain array out of the objects map, that makes selecting with jlinq much easier
				var plainObjects = [];
				$.each(data.objects, function(i, v) {
					$.each(v, function(j, p) {
						plainObjects.push(p);	
					});
				});
				
				$("#objectdiv a").each(function() {
					$(this).click(function() {
						var o = jlinq.from(plainObjects).equals("jsondocId", this.id).first();
						var object = Handlebars.compile($("#object").html());
						var objectHTML = object(o);
						$("#content").html(objectHTML);
						$("#content").show();
						
						$("#testContent").hide();
					});
				});

			},
			error: function(msg) {
				alert("Error " + msg);
			}
		});
	}
	
</script>

</body>
</html>