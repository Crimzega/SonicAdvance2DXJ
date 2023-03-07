package com.sulvic.engine.util;

public class AssetLocation{
	
	private static final String BASE = "sulvic";
	private String assetDomain, assetPath;
	
	private static String cleanDomain(String domain){
		String result = "";
		for(char ch: domain.toCharArray()) result += ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == '_' || ch == '-' || ch == '.')
			? (ch >= 'A' && ch <= 'Z')? ch: Character.toLowerCase(ch)
			: '_';
		return result;
	}
	
	private static String cleanPath(String domain){
		String result = "";
		for(char ch: domain.toCharArray()) result += ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == '/' || ch == '_' || ch == '-' || ch == '.')
			? (ch >= 'a' && ch <= 'z')? ch: Character.toLowerCase(ch)
			: '_';
		return result;
	}
	
	private static String[] splitPath(String domainPath, char splitCh){
		String[] split = new String[]{BASE, domainPath};
		int index = domainPath.indexOf(splitCh);
		if(index != -1){
			if(index >= 0) split[1] = cleanPath(domainPath.substring(index + 1));
			if(index >= 1) split[0] = cleanDomain(domainPath.substring(0, index));
		}
		return split;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		if(obj instanceof AssetLocation){
			AssetLocation assetLoc = (AssetLocation)obj;
			return matches(assetLoc.toString());
		}
		return false;
	}
	
	public boolean matches(String domainPath){
		String[] domainSplit = splitPath(domainPath, ':');
		return assetDomain.equals(domainSplit[0]) && assetPath.equals(domainSplit[1]);
	}
	
	public int hashCode(){ return 31 * assetDomain.hashCode() * assetPath.hashCode(); }
	
	public String getDomain(){ return assetDomain; }
	
	public String getPath(){ return assetPath; }
	
	public String toString(){ return getDomain() + ':' + getPath(); }
	
}
