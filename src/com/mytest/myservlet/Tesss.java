package com.mytest.myservlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tesss {

	public static void main(String[] args) {
		String s = "ÉóºË";
		System.out.println(isNumeric(s));
		
	}
	
	public static boolean isNumeric(String str){ 
		 Pattern pattern = Pattern.compile("-?[0-9]+[.]{0,1}[0-9]*[dD]{0,1}"); 
//		 Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*"); 
		
		 Matcher isNum = pattern.matcher(str);
		 if( !isNum.matches() ){
			return false; 
		 } 
		   return true; 
	}
}
