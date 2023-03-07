package com.sulvic.io;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.sulvic.net.SulvicNet;

public class SystemHandler{
	
	private static void executeFileEvent(File file, FileEvent evt) throws IOException{
		switch(evt){
			case OPEN: SulvicDisplay.DESKTOP.open(file);
			case EDIT: SulvicDisplay.DESKTOP.edit(file);
			case PRINT: SulvicDisplay.DESKTOP.print(file);
		}
	}
	
	public static Clipboard getClipboard(){ return SulvicIO.TOOLKIT.getSystemClipboard(); }
	
	public static void copyToClipboard(Object obj){ copyToClipboard(obj.toString()); }
	
	public static void copyToClipboard(String str){
		StringSelection selection = new StringSelection(str);
		getClipboard().setContents(selection, null);
	}
	
	public static void editFile(File file) throws IOException{ executeFileEvent(file, FileEvent.EDIT); }
	
	public static void openFile(File file) throws IOException{ executeFileEvent(file, FileEvent.OPEN); }
	
	public static void openURL(String link) throws IOException, URISyntaxException{ SulvicDisplay.DESKTOP.browse(new URL(SulvicNet.secureLink(link)).toURI()); }
	
	public static void printFile(File file) throws IOException{ executeFileEvent(file, FileEvent.PRINT); }
	
	public static enum FileEvent{
		OPEN,
		EDIT,
		PRINT;
	}
	
}
