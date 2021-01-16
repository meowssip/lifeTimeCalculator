package com.david.lifeTimeCalculator.controllers;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LTCController {
	public static String[] lifetimeArray;
	public static int varSleepAmount;
	public static int varDeathAge;
	public static int varMyAge;
	
	@RequestMapping("/")
	public String LTCDomain(Model model) {
		return "LTCDomain.jsp";
	}
	@RequestMapping(value="/calculateDeath", method=RequestMethod.POST)
	public String calculateDeath(@RequestParam(value="deathAge")int deathAge, Model model) {
		lifetimeArray = new String[deathAge];
		varDeathAge = deathAge;
		deathAge(deathAge);
		showArray(model);
		return "LTCDomain.jsp";
	}
//	
	public static void deathAge(int deathAge) {
		for(int i=0; i<deathAge;i++) {
			lifetimeArray[i] = "deathAge";
		}
	}
	
	public static void deathAgeInMonths(int deathAge) {
		int yearsToMonths = deathAge*12;
		for(int i=0; i<yearsToMonths;i++) {
			lifetimeArray[i] = "deathAge";
		}
	}
	
	@RequestMapping(value="/calculateAge", method=RequestMethod.POST)
	public String calculateAge(@RequestParam(value="myAge")int myAge, Model model) {
		try {
			if(lifetimeArray.length>myAge) {
				varMyAge = myAge;
				settleArray(myAge, "myAge");
				timeSpendSleeping(varSleepAmount);
				showArray(model);
				return "LTCDomain.jsp";
			}else {
				String errmsg = "Current Age can not be greater than the Life expectancy.";
				model.addAttribute("myAgeErrmsg", errmsg);
				return "LTCDomain.jsp";
			}
		}catch(Exception e){
			String errmsg = "Life expectancy is empty";
			model.addAttribute("myAgeErrmsg", errmsg);
			return "LTCDomain.jsp";
		}
	}
	
	@RequestMapping(value="/calculateSleep", method=RequestMethod.POST)
	public String calculateSleep(@RequestParam(value="sleepAmount")int sleepAmount, Model model) {
		try {
			varSleepAmount = sleepAmount;
			timeSpendSleeping(sleepAmount);
			showArray(model);
			return "LTCDomain.jsp";
		}catch(Exception e){
			String errmsg = "Life expectancy can not be Empty";
			model.addAttribute("sleepErrmsg", errmsg);
			return "LTCDomain.jsp";
		}
		
	}
	
	public static void timeSpendSleeping(int sleepTime) {
		int yearSpendSleeping = sleepingTimeRestOfLife(sleepTime);
		Collections.reverse(Arrays.asList(lifetimeArray));
		settleArray(yearSpendSleeping, "timeSpendSleeping");
		Collections.reverse(Arrays.asList(lifetimeArray));
	}
	
	public static int sleepingTimeRestOfLife(int sleepTime) {
		int day = 24;
		int year = 365;
		int yearsLeftInLifetime = 0;
		for(int i=0; i<lifetimeArray.length;i++) {
			if(lifetimeArray[i] != "myAge") {
				yearsLeftInLifetime++;
			}
		}
		double sleepingYearsInLifetime = Math.round(((double)sleepTime*year*yearsLeftInLifetime)/day/year);
		return (int) sleepingYearsInLifetime;
	}
	
	
	
	public String showArray(Model model) {
		String showArray = Arrays.toString(lifetimeArray).replace(",", "").replace("[", "").replace("]", "").replace("myAge", "<span class='grayDot'></span>").replace("timeSpendSleeping", "<span class='grayDot'></span>").replace("deathAge", "<span class='yellowDot'></span>");
		model.addAttribute("deathAge", varDeathAge)
		.addAttribute("myAge",varMyAge)
		.addAttribute("sleepAmount", varSleepAmount)
		.addAttribute("calculation", showArray)
		.addAttribute("spentTime", new DecimalFormat("#.##").format(((double)varMyAge/varDeathAge*100)) +"(%)")
		.addAttribute("remainingYears", (varDeathAge-varMyAge)+" years")
		.addAttribute("remainingMonths", ((varDeathAge-varMyAge)*12)+" months")
		.addAttribute("remainingWeeks", ((varDeathAge-varMyAge)*52)+" weeks")
		.addAttribute("remainingDays", ((varDeathAge-varMyAge)*365)+" days")
		.addAttribute("lifetimeAsleep", new DecimalFormat("#.#").format(((double)varSleepAmount/24)*100))
		.addAttribute("yearsAsleep", sleepingTimeRestOfLife(varSleepAmount));
		return "LTCDomain.jsp";
	}
	
	public static void settleArray(int numbers, String type) {
		int detect = detect(type);
		for(int i=0;i<numbers;i++) {
			lifetimeArray[i] = type;
		}
		if(lifetimeArray[numbers] == type) {
			for(int i = numbers; i<detect; i++) {
				lifetimeArray[i] = "deathAge";
			}
		}
	}
	
	public static int detect(String type) {
		int detect = 0;
		for(int i=0; i<lifetimeArray.length; i++) {
			if(lifetimeArray[i] == type) {
				detect++;
			}
		}
		return detect;
	}
	
	public static String showDesc(int numbers) {
		String showDesc = ""+numbers;
		return showDesc;
	}
	
	
	
//	
//	public static String[] myAge(String[] myAgeArray, int myAge) {
//		int doubleCheck = 0;
//		for(int i=0; i<myAge;i++) {
//			myAgeArray[i] = "myAge";
//		}
//		lifetimeArray = myAgeArray;
//		return lifetimeArray;
//	}
	
//	public static String[] timeSpendSleeping(String[] sleepTimeArray, int sleepTime) {
//		int timeSpendSleeping = sleepingTimeRestOfLife(sleepTimeArray, sleepTime);
////		System.out.println(timeSpendSleeping);
//		//Reverses array
//		Collections.reverse(Arrays.asList(sleepTimeArray));
//		System.out.println(Arrays.toString(sleepTimeArray));
//		for(int i=0; i<timeSpendSleeping; i++) {
//			sleepTimeArray[i] = "timeSpendSleeping";
//		}
//		Collections.reverse(Arrays.asList(sleepTimeArray));
//		System.out.println(Arrays.toString(sleepTimeArray));
//		lifetimeArray = sleepTimeArray;
//		return lifetimeArray;
//	}
	

//	@RequestMapping(value="/calculateDeath", method=RequestMethod.POST)
//	public static String calculateDeath(@RequestParam(value="deathAge")int deathAge, Model model) {
//		lifetimeArray = new String[deathAge];
//		
//		String[] DeathAgeArray = deathAge(lifetimeArray,deathAge);
//		String showDeathAge = showArray(DeathAgeArray);
//		model.addAttribute("calculation", showDeathAge);
//		
//		return "LTCDomain.jsp";
//	}
	

//	@RequestMapping(value="/calculateSleep", method=RequestMethod.POST)
//	public String calculateSleep(@RequestParam(value="sleepAmount")int sleepAmount, Model model) {
//		String[] timeSpendSleepingArray = timeSpendSleeping(lifetimeArray, sleepAmount);
//		String showTimeSpendSleeping = showArray(timeSpendSleepingArray);
//		model.addAttribute("calculation", showTimeSpendSleeping);
//		return "LTCDomain.jsp";
//	}

//	@RequestMapping(value="/calculateAge", method=RequestMethod.POST)
//	public String calculateAge(@RequestParam(value="myAge")int myAge, Model model) {
////		System.out.println(Integer.toString(myAge).isEmpty());
//		try {
//			if(lifetimeArray.length>myAge) {
//				String[] MyAgeArray = myAge(lifetimeArray, myAge);
//				String showMyAge = showArray(MyAgeArray);
//				model.addAttribute("calculation", showMyAge);
//				return "LTCDomain.jsp";
//			}else {
//				String errmsg = "Current Age can not be greater than the Life expectancy.";
//				model.addAttribute("errmsg", errmsg);
//				return "LTCDomain.jsp";
//			}
//		}catch(Exception e){
//			String errmsg = "Life expectancy is empty";
//			model.addAttribute("errmsg", errmsg);
//			return "LTCDomain.jsp";
//		}
//	}
}
