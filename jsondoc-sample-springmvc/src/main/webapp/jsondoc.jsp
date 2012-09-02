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

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">JSONDoc</a>
			    <div class="navbar-search pull-left">
			    	<input id="jsondocfetch" class="search-query span5" type="text" placeholder="Insert here the JSONDoc URL" value="http://jsondoc-fabiomaffioletti.dotcloud.com/api/jsondoc" />
			    </div>
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

			<div class="span9">
				<div id="content"></div>			
			</div>
		</div>

	</div>

<script id="main" type="text/x-handlebars-template">
<div class="alert alert-info">
	<ul class="unstyled">
		<li id="version">Version: {{version}}</li>
		<li id="basePath">Base path: {{basePath}}</li>
	</ul>
</div>
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
<div class="accordion" id="accordion">
	{{#methods}}
	<div class="accordion-group">
		<div class="accordion-heading">
			<a href="#_{{jsondocId}}" data-parent="#accordion" data-toggle="collapse" class="accordion-toggle">{{path}}</a>
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
						<td>{{verb}}</td>
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
					{{#if urlparameters}}
						<tr>
							<th colspan=2>URL parameters</th>
						</tr>
						{{#each urlparameters}}
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

<script id="object" type="text/x-handlebars-template">
<table class="table table-condensed table-striped table-bordered">
	<tr><th style="width:15%;">Name</th><td><code>{{name}}</code></td></tr>
	{{#if fields}}
	<tr><th colspan=2>Fields</th></tr>
		{{#each fields}}
			<tr><td><code>{{name}}</code></td><td>{{description}}</td></tr>
			<tr><td></td><td>Type: {{type}}</td></tr>
			<tr><td></td><td>Multiple: {{multiple}}</td></tr>
		{{/each}}
	{{/if}}
</table>
</script>

<script>
	$("#jsondocfetch").keypress(function(event) {
		if (event.which == 13) {
			fetchdoc($(this).val());
		}
	});
	
	function fetchdoc(jsondocurl) {
		$.ajax({
			url : jsondocurl,
			type: 'GET',
			dataType: 'json',
			contentType: "application/json; charset=utf-8",
			success : function(data) {
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