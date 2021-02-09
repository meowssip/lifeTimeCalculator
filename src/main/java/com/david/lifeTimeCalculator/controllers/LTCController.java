package com.david.lifeTimeCalculator.controllers;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LTCController {
	
	@RequestMapping("/")
	public String LTCDomain(Model model, HttpSession session) {
		try {
			model.addAttribute("display", showArray((String[]) session.getAttribute("lifetimeArray")))
			.addAttribute("spentTime", new DecimalFormat("#.##").format(((float) (int)session.getAttribute("myAge")/(int)session.getAttribute("deathAge")) *100)+"%")
			.addAttribute("remainingYears", ((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge")))
			.addAttribute("remainingMonths", (((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge"))*12))
			.addAttribute("remainingWeeks", (((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge"))*52))
			.addAttribute("remainingDays", (((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge"))*365))
			.addAttribute("availableTime", new DecimalFormat("#.##").format(((float)(int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge"))/(int)session.getAttribute("deathAge")*100) +"%")
			.addAttribute("asleepPercentage", new DecimalFormat("#.#").format(timeCalc((int)session.getAttribute("sleepAmount"))))
			.addAttribute("awakePercentage", new DecimalFormat("#.#").format((float)100 - timeCalc((int)session.getAttribute("sleepAmount"))))
			.addAttribute("yearsAsleep", new DecimalFormat("#.#").format(yearCalc((int)session.getAttribute("sleepAmount"),((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge")))))
			.addAttribute("yearsAwake", new DecimalFormat("#.#").format(((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge"))-yearCalc((int)session.getAttribute("sleepAmount"),((int)session.getAttribute("deathAge")-(int)session.getAttribute("myAge")))));
			return "LTCDomain.jsp";
		}catch(Exception e) {
			return "LTCDomain.jsp";
		}
		
	}
	
	@RequestMapping(value="/calculateDeath", method=RequestMethod.POST)
	public static String testing(@RequestParam(value="deathAge")int deathAge, HttpSession session) {
		session.setAttribute("deathAge", deathAge);
		session.setAttribute("lifetimeArray",deathAge(deathAge*12));
		return "redirect:/";
	}
	
	public static String[] deathAge(int deathAge) {
		String[] lifetimeArray;
		lifetimeArray = new String[deathAge];
		for(int i=0; i<deathAge;i++) {
			lifetimeArray[i] = "deathAge";
		}
		return lifetimeArray;
	}
	
	@RequestMapping(value="/calculateAge", method=RequestMethod.POST)
	public String calculateAge(@RequestParam(value="myAge")int myAge, HttpSession session, RedirectAttributes redir) {
		new RedirectView("/", true);
		try {
			if(myAge<(int)session.getAttribute("deathAge")) {
					session.setAttribute("myAge" , myAge);
					settleArray((String[]) session.getAttribute("lifetimeArray"),myAge*12, "myAge");
					if(session.getAttribute("sleepAmount")==null) {
						return "redirect:/";
					}else {
						timeSpendSleeping((int)session.getAttribute("sleepAmount"), session);
						return "redirect:/";
					}
			}else {
				String errmsg = "Current Age can not be greater than the Life expectancy.";
				redir.addFlashAttribute("myAgeErrmsg", errmsg);
				return "redirect:/";
			}
		}catch(NullPointerException e) {
			String errmsg = "Life expectancy is missing.";
			redir.addFlashAttribute("myAgeErrmsg", errmsg);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/calculateSleep", method=RequestMethod.POST)
	public String calculateSleep(@RequestParam(value="sleepAmount")int sleepAmount, HttpSession session, RedirectAttributes redir) {
		new RedirectView("/", true);
		try {
			timeSpendSleeping(sleepAmount, session);
			session.setAttribute("sleepAmount", sleepAmount);
			return "redirect:/";
		}catch(Exception e){
			String errmsg = "Life expectancy is missing.";
			redir.addFlashAttribute("sleepErrmsg", errmsg);
			return "redirect:/";
		}
	}
	
	public static void timeSpendSleeping(int sleepTime, HttpSession session) {
		float yearSpendSleeping = sleepingTimeRestOfLife(sleepTime, session);
		Collections.reverse(Arrays.asList((String[]) session.getAttribute("lifetimeArray")));
		settleArray((String[]) session.getAttribute("lifetimeArray"),(int) yearSpendSleeping, "timeSpendSleeping");
		Collections.reverse(Arrays.asList((String[]) session.getAttribute("lifetimeArray")));
	}
	
	public static int sleepingTimeRestOfLife(int sleepTime, HttpSession session) {
		int day = 24;
		int year = 365;
		int numbersLeftInLifetime = 0;
		for(int i=0; i<((String[]) session.getAttribute("lifetimeArray")).length;i++) {
			if(((String[]) session.getAttribute("lifetimeArray"))[i] != "myAge") {
				numbersLeftInLifetime++;
			}
		}
		float sleepingYearsInLifetime = Math.round(((float)sleepTime*year*numbersLeftInLifetime)/day/year);
		return (int) sleepingYearsInLifetime;
	}
	
	public static String showArray(String[] lifetimeArray) {
		String showArray = Arrays.toString(lifetimeArray).replace(",", "").replace("[", "").replace("]", "").replace("myAge", "<span class='grayDot'></span>").replace("timeSpendSleeping", "<span class='grayDot'></span>").replace("deathAge", "<span class='yellowDot'></span>").replace("null", "");
		return showArray;
	}
	
	public static void settleArray(String[] lifetimeArray, int numbers, String type) {
		int detect = detect(lifetimeArray, type);
		for(int i=0;i<numbers;i++) {
			lifetimeArray[i] = type;
		}
		if(lifetimeArray[numbers] == type) {
			for(int i = numbers; i<detect; i++) {
				lifetimeArray[i] = "deathAge";
			}
		}
	}
	
	public static int detect(String[] lifetimeArray, String type) {
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
	
	public float timeCalc(int number) {
		float timeCalc= ((float)number/24)*100;
		return timeCalc;
	}
	
	public float yearCalc(int number, int remainingYears) {
		float yearCalc= (((float)number/24)*remainingYears*365)/365;
		System.out.println(yearCalc);
		return yearCalc;
	}
	
	
//	@RequestMapping("/reset")
//	public String reset(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
	

//	@RequestMapping("/toMonths")
//	public String toMonths(HttpSession session) {
//		try {
//			session.setAttribute("lifetimeArray", deathAge((int)session.getAttribute("deathMonths")));
//			settleArray((String[]) session.getAttribute("lifetimeArray"),(int)session.getAttribute("myAge")*12, "myAge");
//			timeSpendSleeping((int)session.getAttribute("sleepAmount"), session);
//		}catch(Exception e) {
//			
//		}
//		return "redirect:/";
//	}
	
//	@RequestMapping("/toYears")
//	public String toYears(HttpSession session) {
//		try {
//			session.setAttribute("lifetimeArray", deathAge((int)session.getAttribute("deathAge")));
//			settleArray((String[]) session.getAttribute("lifetimeArray"),(int)session.getAttribute("myAge"), "myAge");
//			monthSpendSleeping((int)session.getAttribute("sleepAmount"), session);
//		}catch(Exception e) {
//			
//		}
//		return "redirect:/";
//	}
	
//	@RequestMapping(value="/calculateAge", method=RequestMethod.POST)
//	public String calculateAge(@RequestParam(value="myAge")int myAge, HttpSession session) {
//		
//		try {
//			if(lifetimeArray.length>myAge) {
//				varMyAge = myAge;
//				settleArray(myAge, "myAge");
//				timeSpendSleeping(varSleepAmount);
//				showArray(model);
//				return "LTCDomain.jsp";
//			}else {
//				String errmsg = "Current Age can not be greater than the Life expectancy.";
//				model.addAttribute("myAgeErrmsg", errmsg);
//				return "LTCDomain.jsp";
//			}
//		}catch(Exception e){
//			String errmsg = "Life expectancy is empty";
//			model.addAttribute("myAgeErrmsg", errmsg);
//			return "LTCDomain.jsp";
//		}
//		return "redirect:/";
//	}
	
//	@RequestMapping(value="/calculateDeath1", method=RequestMethod.POST)
//	public String calculateDeath(@RequestParam(value="deathAge")int deathAge, Model model, HttpSession session) {
//		lifetimeArray = new String[deathAge];
//		varDeathAge = deathAge;
//		deathAge(deathAge);
//		showArray(model);
//		return "LTCDomain.jsp";
//	}
//	
	
	
//	public static void deathAgeInMonths(int deathAge) {
//		int yearsToMonths = deathAge*12;
//		for(int i=0; i<yearsToMonths;i++) {
//			lifetimeArray[i] = "deathAge";
//		}
//	}
//	
	
	

//	
//	@RequestMapping(value="/calculateSleep", method=RequestMethod.POST)
//	public String calculateSleep(@RequestParam(value="sleepAmount")int sleepAmount, Model model) {
//		try {
//			varSleepAmount = sleepAmount;
//			timeSpendSleeping(sleepAmount);
//			showArray(model);
//			return "LTCDomain.jsp";
//		}catch(Exception e){
//			String errmsg = "Life expectancy can not be Empty";
//			model.addAttribute("sleepErrmsg", errmsg);
//			return "LTCDomain.jsp";
//		}
//		
//	}
//	
//	public static void timeSpendSleeping(int sleepTime) {
//		int yearSpendSleeping = sleepingTimeRestOfLife(sleepTime);
//		Collections.reverse(Arrays.asList(lifetimeArray));
//		settleArray(yearSpendSleeping, "timeSpendSleeping");
//		Collections.reverse(Arrays.asList(lifetimeArray));
//	}
//	
//	public static int sleepingTimeRestOfLife(int sleepTime) {
//		int day = 24;
//		int year = 365;
//		int yearsLeftInLifetime = 0;
//		for(int i=0; i<lifetimeArray.length;i++) {
//			if(lifetimeArray[i] != "myAge") {
//				yearsLeftInLifetime++;
//			}
//		}
//		double sleepingYearsInLifetime = Math.round(((double)sleepTime*year*yearsLeftInLifetime)/day/year);
//		return (int) sleepingYearsInLifetime;
//	}
//	
//	
	
	
	
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
