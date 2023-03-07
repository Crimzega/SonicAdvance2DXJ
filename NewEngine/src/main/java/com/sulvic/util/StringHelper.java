package com.sulvic.util;

public class StringHelper{
	
	public static boolean isNullOrEmpty(String str){ return str == null || str.equals(""); }
	
	public static String substringBefore(String str, String cutoff){
		int index = str.indexOf(str);
		return index != -1? str.substring(0, index): str;
	}
	
	public static String substringAfter(String str, String cutoff){
		int index = str.indexOf(cutoff);
		return index != -1? str.substring(index + cutoff.length()): str;
	}
	
	public static String padStart(String str, char padding, int length){
		String result = "";
		while(result.length() < length) result = padding + result;
		return result;
	}
	
	public static String padEnd(String str, char padding, int length){
		String result = "";
		while(result.length() < length) result += padding;
		return result;
	}
	
	public static String reverse(String str){
		String result = "";
		for(int i = 0; i < str.length(); i++) result += str.charAt(str.length() - i - 1);
		return result;
	}
	
}
