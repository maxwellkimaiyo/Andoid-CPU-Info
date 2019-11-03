package com.max.easyhub.cpux.tools;

public class StrFormatter {

	public static String[] splitEveryLIne(String str){
		String s[]= new String[2];
		if(str.contains("\n")){
			s=str.split("\\\n");
		}
		return s;
	}
	
	public static String[] splitWitDoubleDot(String str){
		String s[]= new String[2];
		if(str.contains(":")){
			s=str.split("\\:");
		}
		return s;
	}
	
	public static String getFormattedName(String s){
		String result=s;

		char[] c = s.toCharArray();
		result = String.valueOf(c[0]).toUpperCase();
		for (int i = 1; i < c.length; i++) {
			if (c[i] == '_') {
				result = result + " ";
			} else if (c[i - 1] == '_' || c[i - 1] == ' ') {
				result = result + String.valueOf(c[i]).toUpperCase();
			} else {
				result = result + c[i];
			}
		}
		
		return result;
	}
}
