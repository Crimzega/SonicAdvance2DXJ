package com.sulvic.io;

import java.io.File;
import java.util.List;
import java.util.zip.ZipFile;

import com.sulvic.util.ContentBuilder;

public class FileHelper{
	
	public static List<File> getFilesByExtension(File folder, String ext){
		List<File> result = ContentBuilder.newArrayList();
		for(File file: folder.listFiles()) if(getFileExtension(file).endsWith(ext)) result.add(file);
		return result;
	}
	
	public static boolean createFolder(File folder){ return folder.mkdirs(); }
	
	public static boolean createFolder(String folder){ return createFolder(new File(folder)); }
	
	public static boolean pathExists(File file){ return file.exists(); }
	
	public static boolean pathExists(String file){ return pathExists(new File(file)); }
	
	public static boolean pathExists(ZipFile file){ return pathExists(file.getName()); }
	
	public static boolean pathsExist(File... files){
		boolean flag = pathExists(files[0]);
		for(int i = 1; i < files.length; i++) {
			flag &= pathExists(files[i]);
			if(!flag) break;
		}
		return flag;
	}
	
	public static boolean pathsExist(String... files){
		boolean flag = pathExists(files[0]);
		for(int i = 1; i < files.length; i++) {
			flag &= pathExists(files[i]);
			if(!flag) break;
		}
		return flag;
	}
	
	public static boolean pathsExist(ZipFile... files){
		boolean flag = pathExists(files[0]);
		for(int i = 1; i < files.length; i++) {
			flag &= pathExists(files[i]);
			if(!flag) break;
		}
		return flag;
	}
	
	public static File getDrive(File file){ return new File(file.getAbsolutePath().substring(0, 3)); }
	
	public static String getFileName(File file){ return getFileName(file, true); }
	
	public static String getFileName(File file, boolean withExt){
		String name = file.getAbsolutePath();
		name = name.substring(name.lastIndexOf(File.separator) + 1);
		return withExt? name: name.substring(name.lastIndexOf('.'));
	}
	
	public static String getFileExtension(File file){
		String name = getFileName(file);
		return name.substring(name.lastIndexOf('.') + 1);
	}
	
}
