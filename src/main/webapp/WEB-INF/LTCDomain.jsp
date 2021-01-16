<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/LTCController.css">
<script type="text/javascript" src="js/LTCController.js"></script>
<meta charset="UTF-8">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<title>Insert title here</title>
</head>
<body>
	<div class="titleDiv">
		<h1 id="title"><a href="/">Life<span id="titleColor">time Calc</span>ulator</a></h1>
	</div>
	<div id="lineDiv"></div>
	<div class="head">
		<br>
		<div>
			<form id ="input" method="POST" action="/calculateDeath">
				<input class="inputBox" type="number" required="required" name="deathAge" min="1" max="200" placeholder="Life expectancy (years)" autocomplete="off">
				<input class="calcButton" type="submit" value="Calculate" />
			</form><br>
			<span class="errmsg">${myAgeErrmsg}</span>
			<form id ="input" method="POST" action="/calculateAge">
				<input  class="inputBox" type="number" required="required" name="myAge" min="1" max="200" placeholder="Current Age" autocomplete="off">
				<input  class="calcButton" type="submit" value="Calculate" />
			</form><br>
			<span class="errmsg">${sleepErrmsg}</span>
			<form id ="input"  method="POST" action="/calculateSleep">
				<input  class="inputBox" required="required" type="number" name="sleepAmount" min="1" max="24" placeholder="(around) Hours of sleep a day" autocomplete="off">
				<input class="calcButton" type="submit" value="Calculate" />
			</form>
		</div>
		<br>
		<div id="lineDiv"></div>
	</div>
	<div class="demonstrateDiv">
		<span><p class="tips">Each circle demonstrates a year</p></span>
		<p class="dots"><span>${calculation}</span></p>
	</div>
	
	<div id="lineDiv"></div>
	<div>
		<p id="stat1">Life Expectancy : <span class="highlight">${deathAge}</span> years.</p>
		<p id="stat2">Current Age : <span class="highlight">${myAge}</span> years old.</p>
		<p id="stat3">Sleeps for : <span class="highlight">${sleepAmount}</span> hours a day.</p>
	</div>
		
	<div id="lineDiv"><p id="flip" class="tips">Read More</p></div>
	<div class="body">
		<div id="detailDiv" class="detailDiv">
			<div class="detailDiv100">
					<p>Life Spent : <span class="highlight">${spentTime}</span></p>
			</div>
		
			<div id="detailDivlineDiv"></div>
	
			<div class="detailDiv100">
				<div class="detailDiv25" >
					<br>
				</div>
				<div class="detailDiv25" >
					<br>
					<p>Remaining Lifetime :</p>
				</div>
				<div class="detailDivChild">
					<p><span class="highlight">${remainingYears}</span></p>
					<p><span class="highlight">${remainingMonths}</span></p>
					<p><span class="highlight">${remainingWeeks}</span></p>
					<p><span class="highlight">${remainingDays}</span></p>
				</div>
				<div class="detailDiv25" >
					<br>
				</div>
			</div>	
			
			<div id="detailDivlineDiv"></div>
			
			<div class="detailDiv100">
				<p><span class="highlight">${sleepAmount} hours</span> of sleep a day is spending </p>
				<p>total <span class="highlight">${lifetimeAsleep}% (${yearsAsleep} years)</span>  of remaining lifetime Asleep.</p>
			</div>
		</div>
	</div>
</body>
</html>