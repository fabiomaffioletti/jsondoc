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
<script type="text/javascript" src="js/handlebars-1.0.0.beta.6.js"></script>
<script type="text/javascript" src="js/jlinq.js"></script>
<script type="text/javascript" src="js/prettify.js"></script>
<script src="js/bootstrap-button.js"></script>

<!-- Le styles -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet" >
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

.GET {
	padding-top : 3px;
	background-color: #468847;
}
.POST {
	padding-top : 3px;
	background-color: #3A87AD;
}
.PUT {
	padding-top : 3px;
	background-color: #F89406;
}
.DELETE {
	padding-top : 3px;
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
</style>
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>

<body>

	<div class="navbar navbar-fixed-top navbar-inverse">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="#">JSONDoc</a>
				    <form class="navbar-form pull-left">
					    <input id="jsondocfetch" class="span5" type="text" placeholder="Insert here the JSONDoc URL" autocomplete="off" />
					    <button id="getDocButton" class="btn">Get documentation</button>
					</form>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			
			<div class="span3">
				<div id="maindiv" style="display:none;"></div>
				<div class="well sidebar-nav" id="apidiv" style="display:none;"></div>
				<div class="well sidebar-nav" id="objectdiv" style="display:none;"></div>
			</div>

			<div class="span5">
				<div id="content"></div>			
			</div>
			
			<div class="span4">
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
<ul class="nav nav-list">
	<li class="nav-header">APIs</li>
	{{#apis}}
		<li><a href="#" id="{{jsondocId}}" rel="api">{{name}}</a></li>
	{{/apis}}
</ul>
</script>

<script id="objects" type="text/x-handlebars-template">
<ul class="nav nav-list">
	<li class="nav-header">Objects</li>
	{{#objects}}
		<li><a href="#" id="{{jsondocId}}" rel="object">{{name}}</a></li>
	{{/objects}}
</ul>
</script>

<script id="methods" type="text/x-handlebars-template">
<blockquote>
  <p style="text-transform: uppercase;"><span id="apiName"></span></p>
  <small><span id="apiDescription"></span></cite></small>
</blockquote>

<div class="accordion" id="accordion">
	{{#methods}}
	<div class="accordion-group">
		<div class="accordion-heading">
			<span class="label pull-right {{verb}}" style="margin-right: 5px; margin-top: 8px;">{{verb}}</span>
			<a href="#_{{jsondocId}}" id="{{jsondocId}}" rel="method" data-parent="#accordion" data-toggle="collapse" class="accordion-toggle">{{path}}</a>
		</div>
		<div class="accordion-body collapse" id="_{{jsondocId}}">
			<div class="accordion-inner">
				<table class="table table-condensed table-striped table-bordered">
					<tr>
						<th style="width:15%;">Path</th>
						<td><code>{{path}}</code></td>
					</tr>
					<tr>
						<th>Description</th>
						<td>{{description}}</td>
					</tr>
					<tr>
						<th>Method</th>
						<td><span class="label {{verb}}">{{verb}}</span></td>
					</tr>
					{{#if produces}}
						<tr>
							<th colspan=2>Produces</th>
						</tr>
						{{#each produces}}
							<tr>
								<td colspan=2><code>{{this}}</code></td>
							</tr>
						{{/each}}
					{{/if}}
					{{#if consumes}}
						<tr>
							<th colspan=2>Consumes</th>
						</tr>
						{{#each consumes}}
							<tr>
								<td colspan=2><code>{{this}}</code></td>
							</tr>
						{{/each}}
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
							<tr>
								<td></td>
								<td>Type: {{this.type}}</td>
							</tr>
							{{#if this.description}}
							<tr>
								<td></td>
								<td>Description: {{this.description}}</td>
							</tr>
							{{/if}}
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
							<tr>
								<td></td>
								<td>Type: {{this.type}}</td>
							</tr>
							{{#if this.description}}
							<tr>
								<td></td>
								<td>Description: {{this.description}}</td>
							</tr>
							{{/if}}
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
							<td>Object</td>
							<td><code>{{bodyobject.object}}</code></td>
						</tr>
						<tr>
							<td>Multiple</td>
							<td>{{bodyobject.multiple}}</td>
						</tr>
						{{#if bodyobject.map}}
							<tr>
								<td>Map key</td>
								<td><code>{{bodyobject.mapKeyObject}}</code></td>
							</tr>
							<tr>
								<td>Map value</td>
								<td><code>{{bodyobject.mapValueObject}}</code></td>
							</tr>
						{{/if}}
					{{/if}}
					{{#if response}}
						<tr>
							<th colspan=2>Response object</th>
						</tr>
						<tr>
							<td>Object</td>
							<td><code>{{response.object}}</code></td>
						</tr>
						<tr>
							<td>Multiple</td>
							<td>{{response.multiple}}</td>
						</tr>
						{{#if response.map}}
							<tr>
								<td>Map key</td>
								<td><code>{{response.mapKeyObject}}</code></td>
							</tr>
							<tr>
								<td>Map value</td>
								<td><code>{{response.mapValueObject}}</code></td>
							</tr>
						{{/if}}
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

<div class="row-fluid">

	{{#if headers}}	
	<div class="span12">
		<div id="headers">
			<h4>Headers</h4>
			{{#headers}}
				<div class="input-prepend">
					<span style="text-align:left;" class="add-on span4">{{name}}</span><input type="text" class="span8" name="{{name}}" placeholder="{{name}}">
				</div>
			{{/headers}}
		</div>
	</div>
	{{/if}}

	{{#if produces}}
		<div class="span6" style="margin-left:0px">		
		<div id="produces" class="playground-spacer">
		<h4>Accept</h4>	
		{{#produces}}
			<label class="radio"><input type="radio" name="produces" value="{{this}}">{{this}}</label>
		{{/produces}}
		</div>
		</div>
	{{/if}}

	{{#if bodyobject}}
	{{#if consumes}}
		<div class="span6" style="margin-left:0px">		
		<div id="consumes" class="playground-spacer">
		<h4>Content type</h4>	
		{{#consumes}}
			<label class="radio"><input type="radio" name="consumes" value="{{this}}">{{this}}</label>
		{{/consumes}}
		</div>
		</div>
	{{/if}}
	{{/if}}

	{{#if pathparameters}}
	<div class="span12" style="margin-left:0px">
		<div id="pathparameters" class="playground-spacer">
			<h4>Path parameters</h4>
			{{#pathparameters}}
				<div class="input-prepend">
					<span style="text-align:left;" class="add-on span4">{{name}}</span><input type="text" class="span8" name="{{name}}" placeholder="{{name}}">
				</div>
			{{/pathparameters}}
		</div>
	</div>
	{{/if}}

	{{#if queryparameters}}
	<div class="span12" style="margin-left:0px">
		<div id="queryparameters" class="playground-spacer">
			<h4>Query parameters</h4>
			{{#queryparameters}}
				<div class="input-prepend">
					<span style="text-align:left;" class="add-on span4">{{name}}</span><input type="text" class="span8" name="{{name}}" placeholder="{{name}}">
				</div>
			{{/queryparameters}}
		</div>
	</div>
	{{/if}}

	{{#if bodyobject}}
	<div class="span12" style="margin-left:0px">
		<div id="bodyobject" class="playground-spacer">
			<h4>Body object</h4>
			<textarea class="span12" id="inputJson" rows=10 />
		</div>
	</div>
	{{/if}}

	<div class="span12" style="margin-left:0px">
		<div class="form-actions">
			<button class="btn btn-primary" id="testButton" data-loading-text="Loading...">Submit</button>
		</div>
	</div>

</div>

<div class="tabbable" id="resInfo" style="display:none;">
	<ul class="nav nav-tabs">
  		<li class="active"><a href="#tab1" data-toggle="tab">Response text</a></li>
  		<li><a href="#tab2" data-toggle="tab">Response info</a></li>
  		<li><a href="#tab3" data-toggle="tab">Request info</a></li>
	</ul>
	<div class="tab-content">
    	<div class="tab-pane active" id="tab1">
    		<pre id="response" class="prettyprint">
			</pre>
   		</div>
    	<div class="tab-pane" id="tab2">
			<p class="nav-header" style="padding:0px">Response code</p>
      		<pre id="responseStatus" class="prettyprint">
			</pre>
			<p class="nav-header" style="padding:0px">Response headers</p>
      		<pre id="responseHeaders" class="prettyprint">
			</pre>
    	</div>
		<div class="tab-pane" id="tab3">
      		<p class="nav-header" style="padding:0px">Request URL</p>
      		<pre id="requestURL" class="prettyprint">
			</pre>
    	</div>
	</div>
</div>

</script>

<script id="object" type="text/x-handlebars-template">
<table class="table table-condensed table-striped table-bordered">
	<tr><th style="width:15%;">Name</th><td><code>{{name}}</code></td></tr>
	{{#if description}}
		<tr><th>Description</th><td>{{description}}</td></tr>
	{{/if}}
	{{#if fields}}
	<tr><th colspan=2>Fields</th></tr>
		{{#each fields}}
			<tr><td><code>{{name}}</code></td><td>{{description}}</td></tr>
			<tr><td></td><td>Type: {{type}}</td></tr>
			<tr><td></td><td>Multiple: {{multiple}}</td></tr>
			{{#if map}}
				{{#if this.mapKeyObject}}
				<tr>	
					<td></td>
					<td>Map key: {{this.mapKeyObject}}</td>
				</tr>
				<tr>
					<td></td>
					<td>Map value: {{this.mapValueObject}}</td>
				</tr>
				{{/if}}
			{{/if}}
		{{/each}}
	{{/if}}
</table>
</script>

<script>
	var model;
	
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
				
				$("#apidiv a").each(function() {
					$(this).click(function() {
						var api = jlinq.from(data.apis).equals("jsondocId", this.id).first();
						var methods = Handlebars.compile($("#methods").html());
						var methodsHTML = methods(api);
						$("#content").html(methodsHTML);
						$("#content").show();
						$("#apiName").text(api.name);
						$("#apiDescription").text(api.description);
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
				
				$("#objectdiv a").each(function() {
					$(this).click(function() {
						var o = jlinq.from(data.objects).equals("jsondocId", this.id).first();
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