<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/LTCController.css">
<%@taglib  prefix="spring" uri="http://www.springframework.org/tags" %> 
<script type="text/javascript" src="js/LTCController.js"></script>
<meta charset="UTF-8">
<meta name="keywords" content="java, life, lifetime, calculator, lifetime calculator, java spring">
<meta name="description" content="Calculates lifetime">
<meta name="author" content="David Yoon">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<title>David Yoon</title>
</head>
<body>
	<div class="basic">
		<div class="titleDiv">
			<h1 id="title"><a href="/">Life<span id="titleColor">time calc</span>ulator</a></h1>
		</div>
		<div id="lineDiv"></div>
		<div class="head">
			<br>
			<div>
				<form id ="input1" method="POST" action="/calculateDeath">
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
			<span><p class="tips">Each circle demonstrates a month</p></span>
			<p class="dots"><span>${display}</span></p>
		</div>
		
		<div id="lineDiv"></div>
		<div >
			<a class="quotes"><h3>"If you truly love life,</h3>
			<h3>don't waste time</h3>
			<h3>because time is what life is made of."</h3>
			<h3>- Bruce Lee</h3></a>
		</div>
		
		<div  id="lineDiv"><p id="flip" class="tips">Read More</p></div>
		<div class="body">	
			<div id="detailDiv">
				<div class="detailDiv100">
					<p >Life Expectancy : <span class="highlight">${deathAge}</span> years.</p>
					<p >Current Age : <span class="highlight">${myAge}</span> years old.</p>
					<p>Sleeps for : <span class="highlight">${sleepAmount}</span> hours a day.</p>
				</div>
				
				<div id="detailDivlineDiv"></div>
				
				<div class="detailDiv100">
						<p>Life Spent : <span class="highlight">${spentTime}</span></p>
				</div>
			
				<div id="detailDivlineDiv"></div>
		
				<div class="detailDiv100">
					<div class="detailDiv25" >
						<br>
					</div>
					<div class="detailDiv25" >
						<p style="margin-top: 70px;">Remaining time :</p>
					</div>
					<div class="detailDivChild">
						<p><span class="highlight">${remainingYears} (${availableTime})</span> years.</p>
						<p><span class="highlight">${remainingMonths}</span> in months.</p>
						<p><span class="highlight">${remainingWeeks}</span> in weeks.</p>
						<p><span class="highlight">${remainingDays}</span> in days.</p>
					</div>
					<div class="detailDiv25" >
						<br>
					</div>
				</div>	
				
				<div id="detailDivlineDiv"></div>
				
				<div class="detailDiv100">
					<p><span class="highlight">${sleepAmount} hours</span> of sleep a day is spending about</p>
					<p><span class="highlight">${yearsAsleep} years (${asleepPercentage}%)</span>  of the remaining lifetime asleep</p>
					<p>and <span class="highlight">${yearsAwake} years (${awakePercentage}%)</span> awake.</p>
				</div>
			</div>
		</div>
		<br>
	
		
		<div id="lineDiv"></div>
		
		
		
			<p class="tips">Inspired By</p>
		<div class="motive">
			<iframe src="https://www.youtube.com/embed/arj7oStGLkU" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		</div>
		
		<div id="lineDiv"></div>
		<div style="margin:auto;">
			<p style="display:inline-block;margin-right: 10px;"class="tips">Personal Project <a style="vertical-align: sub;display:inline-block;"href="https://www.linkedin.com/in/david-yoon-937567204/" target="_blank" ><img src="<spring:url value='/resources/linkedin.png'/>" style="width:25px;"></a></p>
		</div>
	</div>
	
	
</body>
</html>