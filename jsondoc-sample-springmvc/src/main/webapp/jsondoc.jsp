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

<!-- Le styles -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet" >
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link href="../css/bootstrap-responsive.min.css" rel="stylesheet">

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
			    	<input id="jsondocfetch" class="search-query span3" type="text" placeholder="Insert here the JSONDoc URL" value="http://localhost:8080/api/jsondoc" />
			    </div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			
			<div class="span3">
				<div id="apidiv" style="display:none;">
					<div class="alert alert-info">
						<ul class="unstyled">
							<li id="version"></li>
							<li id="basePath"></li>
						</ul>
					</div>
				</div>
				<div class="well sidebar-nav">
					<ul class="nav nav-list" id="apis"></ul>
				</div>
				<div class="well sidebar-nav">
					<ul class="nav nav-list" id="objects"></ul>
				</div>
			</div>

			<div class="span9">
				<div id="content"></div>			
			</div>
		</div>

		<hr>

		<footer>
			<p>&copy; JSONDoc 2012</p>
		</footer>

	</div>

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
				buildApiDiv(data);
				buildApis(data);
				buildObjects(data);
				console.debug(data);
			},
			error: function(msg) {
				console.debug(msg);
			}
		});
	}
	
	function buildApiDiv(data) {
		$("#version").text("Version: " + data.version);
		$("#basePath").text("Base path: " + data.basePath);
		$("#apidiv").show();
	}
	
	function buildApis(data) {
		var as = $("#apis");
		var cs = $("#content");
		$(as).empty();
		$(cs).empty();
		$("<li/>", {'class' : 'nav-header', text : 'Apis'}).appendTo(as);
		$(data.apis).each(function(index) {
			$("<a/>", {text: this.path, href: '#', id: index+'a', rel: 'api'}).appendTo($("<li/>").appendTo(as));
		});
		$("a[rel=api]").each(function() {
			$(this).click(function() {
				var i = this.id.substring(0,1);
				var api = data.apis[i];
				$(cs).empty();
				var accordion = $("<div/>", {'class': 'accordion', id: 'accordion'}).appendTo(cs);
				$(api.methods).each(function(index) {
					var accordionGroup = $("<div/>", {'class' : 'accordion-group'}).appendTo(accordion);
					var accordionHeading = $("<div/>", {'class': 'accordion-heading'}).appendTo(accordionGroup);
					var accordionA = $("<a/>", {href: '#'+index, 'data-parent': '#accordion', 'data-toggle': 'collapse', 'class': 'accordion-toggle', text: this.path}).appendTo(accordionHeading);
					var accordionBody = $("<div/>", {'class': 'accordion-body collapse', id: index}).appendTo(accordionGroup);
	                var accordionInner = $("<div/>", {'class': 'accordion-inner'}).appendTo(accordionBody);
					
					var table = $('<table/>', {'class' : 'table table-condensed table-striped table-bordered'}).appendTo(accordionInner);
					var tr = $('<tr/>').appendTo(table);
					var th = $('<th/>', {text: 'Path', style: 'width: 15%;'}).appendTo(tr);
					$('<code/>', {text: this.path}).appendTo($('<td/>').appendTo(tr));
					tr = $('<tr/>').appendTo(table);
					th = $('<th/>', {text: 'Description'}).appendTo(tr);
					td = $('<td/>', {text: this.description}).appendTo(tr);
					tr = $('<tr/>').appendTo(table);
					th = $('<th/>', {text: 'Method'}).appendTo(tr);
					td = $('<td/>').appendTo(tr);
					
					if(this.method == 'GET') {
						var labelClass = 'label label-info';
					} else if(this.method == 'POST') {
						var labelClass = 'label label-success';
					} else if(this.method == 'PUT') {
						var labelClass = 'label label-warning';
					} else if(this.method == 'DELETE') {
						var labelClass = 'label label-important';
					}
					
					$("<span/>", {'class' : labelClass, text: this.method}).appendTo(td);
					tr = $('<tr/>').appendTo(table);
					th = $('<th/>', {text: 'URL parameters', colspan: 2}).appendTo(tr);
					buildURLParameters(this.urlparameters, table);
					tr = $('<tr/>').appendTo(table);
					th = $('<th/>', {text: 'Body parameter', colspan: 2}).appendTo(tr);
					buildBodyParameter(this.bodyparameter, table);
					tr = $('<tr/>').appendTo(table);
					th = $('<th/>', {text: 'Response', colspan: 2}).appendTo(tr);
					buildResponseObject(this.response, table);
					tr = $('<tr/>').appendTo(table);
					th = $('<th/>', {text: 'Errors', colspan: 2}).appendTo(tr);
					buildErrors(this.apierrors, table);
				});
			});
		});
		
		$(".collapse").collapse();
	}
	
	function buildURLParameters(urlparameters, t) {
		if(urlparameters.length == 0) {
			tr = $('<tr/>').appendTo(t);
			$('<td/>', {text: 'No URL parameters found', colspan : 2}).appendTo(tr);
		}
		$(urlparameters).each(function() {
			tr = $('<tr/>').appendTo(t);
			$('<code/>', {text: this.name}).appendTo($('<td/>').appendTo(tr));
			$('<td/>', {text: 'Description: ' + this.description}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Required: ' + this.required}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Type: ' + this.type}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Allowed values: ' + this.allowedvalues}).appendTo(tr);
		});
	}
	
	function buildBodyParameter(bodyparameter, t) {
		if(bodyparameter != null) {
			tr = $('<tr/>').appendTo(t);
			$('<code/>', {text: bodyparameter.object}).appendTo($('<td/>').appendTo(tr));
			$('<td/>', {text: 'Description: ' + bodyparameter.description}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Multiple: ' + bodyparameter.multiple}).appendTo(tr);
		} else {
			tr = $('<tr/>').appendTo(t);
			$('<td/>', {text: 'No body parameter found', colspan : 2}).appendTo(tr);
		}
	}
	
	function buildResponseObject(response, t) {
		if(response != null) {
			tr = $('<tr/>').appendTo(t);
			$('<code/>', {text: response.object}).appendTo($('<td/>').appendTo(tr));
			$('<td/>', {text: 'Description: ' + response.description}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Multiple: ' + response.multiple}).appendTo(tr);
		} else {
			tr = $('<tr/>').appendTo(t);
			$('<td/>', {text: 'No response object found', colspan : 2}).appendTo(tr);
		}
	}
	
	function buildErrors(apierrors, t) {
		if(apierrors.length == 0) {
			tr = $('<tr/>').appendTo(t);
			$('<td/>', {text: 'No errors found', colspan : 2}).appendTo(tr);
		}
		$(apierrors).each(function() {
			tr = $('<tr/>').appendTo(t);
			$('<code/>', {text: this.code}).appendTo($('<td/>').appendTo(tr));
			$('<td/>', {text: this.description}).appendTo(tr);
		});
	}
	
	function buildObjects(data) {
		var os = $("#objects");
		var cs = $("#content");
		$(os).empty();
		$(cs).empty();
		$("<li/>", {'class' : 'nav-header', text : 'Objects'}).appendTo(os);
		$(data.objects).each(function(index) {
			$("<a/>", {text: this.name, href: '#', id: index+'o', rel: 'object'}).appendTo($("<li/>").appendTo(os));
		});
		$("a[rel=object]").each(function() {
			$(this).click(function() {
				var i = this.id.substring(0,1);
				var object = data.objects[i];
				$(cs).empty();
				var table = $('<table/>', {'class' : 'table table-condensed table-striped table-bordered'}).appendTo(cs);
				var tr = $('<tr/>').appendTo(table);
				var th = $('<th/>', {text: 'Name', style: 'width: 15%;'}).appendTo(tr);
				$('<code/>', {text: object.name}).appendTo($('<td/>').appendTo(tr));
				tr = $('<tr/>').appendTo(table);
				th = $('<th/>', {text: 'Fields', colspan: 2}).appendTo(tr);
				buildObjectFields(object.fields, table);
			});
		});
	}
	
	function buildObjectFields(objectfields, t) {
		if(objectfields.length == 0) {
			tr = $('<tr/>').appendTo(t);
			$('<td/>', {text: 'No fields found', colspan : 2}).appendTo(tr);
		}
		$(objectfields).each(function() {
			tr = $('<tr/>').appendTo(t);
			$('<code/>', {text: this.name}).appendTo($('<td/>').appendTo(tr));
			$('<td/>', {text: 'Description: ' + this.description}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Type: ' + this.type}).appendTo(tr);
			tr = $('<tr/>').appendTo(t);
			$('<td/>').appendTo(tr);
			$('<td/>', {text: 'Multiple: ' + this.multiple}).appendTo(tr);
		});
	}
	
</script>

</body>
</html>