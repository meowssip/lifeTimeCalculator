<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- <script>
$(document).ready(function(){
	$("form").on("submit",function (e) {
	    e.preventDefault(); //To not refresh the page
	    $("form").remove(); //Remove the form
	});
});


</script> -->

<link rel="stylesheet" type="text/css" href="css/LTCController.css">
<script type="text/javascript" src="js/LTCController.js"></script>
<meta charset="UTF-8">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<title>Insert title here</title>
</head>
<body>
	<br>
	<br>
	<br>
	<h1 id="title"><a href="/">Lifetime Calculator</a></h1>
	<div>
		<form method="POST" action="/calculateDeath">
			<label for="deathAge">Life expectancy :</label>
			<input type="number" name="deathAge" min="1" placeholder="years"><br>
			<input type="submit" value="Calculate" />
		</form>
		<p>${errmsg}</p>
		<form method="POST" action="/calculateAge">
			<label for="myAge">Current Age :</label>
			<input type="number" name="myAge"><br>
			<input type="submit" value="Calculate" />
		</form>
	</div>
	<div>
		<p id="displayLifeTime">${deathAge}</p>
		
	</div>
</body>
</html>