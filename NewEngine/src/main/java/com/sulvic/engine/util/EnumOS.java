package com.sulvic.engine.util;

import java.io.File;

public enum EnumOS{
	
	LINUX,
	SOLARIS,
	WINDOWS,
	MACOS,
	UNKNOWN;
	
	public static EnumOS byOsName(){
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.contains("linux") || osName.contains("unix")) return LINUX;
		if(osName.contains("solaris") || osName.contains("sunos")) return SOLARIS;
		if(osName.contains("windows")) return WINDOWS;
		if(osName.contains("macos")) return MACOS;
		return UNKNOWN;
	}
	
	public File getGamePath(String name){
		String appdata, userHome = System.getProperty("user.home", ".");
		switch(this){
			case LINUX:
			case SOLARIS:
				return new File(userHome, '.' + name + '/');
			case WINDOWS:
				appdata = System.getenv("APPDATA");
				return new File(appdata != null? appdata: userHome, '.' + name + '/');
			case MACOS:
				return new File(userHome, "/Library/Application Support/" + name);
			default:
				return new File(userHome, name + '/');
		}
	}
	
}
