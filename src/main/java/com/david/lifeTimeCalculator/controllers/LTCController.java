package com.david.lifeTimeCalculator.controllers;

import java.util.Arrays;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LTCController {
	public static String[] lifeTimeArray;
	@RequestMapping("/")
	public String LTCDomain() {
		return "LTCDomain.jsp";
	}
	
	@RequestMapping(value="/calculateDeath", method=RequestMethod.POST)
	public static String calculateDeath(@RequestParam(value="deathAge")int deathAge, Model model) {
		System.out.println(Integer.toString(deathAge));
		lifeTimeArray = new String[deathAge];
		
		String[] DeathAgeArray = deathAge(lifeTimeArray,deathAge);
		String showDeathAge = showArray(DeathAgeArray);
		
		
		model.addAttribute("deathAge", showDeathAge);
		
		return "LTCDomain.jsp";
	}
	
	@RequestMapping(value="/calculateAge", method=RequestMethod.POST)
	public String calculateAge(@RequestParam(value="myAge")int myAge, Model model) {
//		System.out.println(Integer.toString(myAge).isEmpty());
		try {
			if(lifeTimeArray.length>myAge) {
				String[] MyAgeArray = myAge(lifeTimeArray, myAge);
				String showMyAge = showArray(MyAgeArray);
				model.addAttribute("deathAge", showMyAge);
				return "LTCDomain.jsp";
			}else {
				String errmsg = "Current Age can not be greater than the Life expectancy.";
				model.addAttribute("errmsg", errmsg);
				
				return "LTCDomain.jsp";
			}
		}catch(Exception e){
			String errmsg = "Life expectancy is empty";
			model.addAttribute("errmsg", errmsg);
			
			return "LTCDomain.jsp";
		}
		
		
	}
	
	public static String[] deathAge(String[] deathAgeArray,int deathAge) {
		for(int i=0; i<deathAge;i++) {
			deathAgeArray[i] = "deathAge";
		}
		lifeTimeArray = deathAgeArray;
		return lifeTimeArray;
	}
	
	public static String[] myAge(String[] myAgeArray, int myAge) {
		for(int i=0; i<myAge;i++) {
			myAgeArray[i] = "myAge";
		}
		lifeTimeArray = myAgeArray;
		return lifeTimeArray;
	}
	
	public static String showArray(String[] array) {
		String showArray = Arrays.toString(array).replace(",", "").replace("[", "").replace("]", "");
		return showArray;
	}
}
