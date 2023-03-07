package com.sulvic.io;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class SulvicDisplay{
	
	private static final GraphicsEnvironment ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
	protected static final Desktop DESKTOP = Desktop.getDesktop();
	
	public static DisplayMode[] getDisplayModesForDevice(int index){ return getDevices()[index].getDisplayModes(); }
	
	public static Font[] getFonts(){ return ENVIRONMENT.getAllFonts(); }
	
	public static GraphicsDevice[] getDevices(){ return ENVIRONMENT.getScreenDevices(); }
	
	public static Dimension getDimensionFromDisplayMode(DisplayMode mode){ return new Dimension(mode.getWidth(), mode.getHeight()); }
	
	public static DisplayMode getCurrentDisplayMode(){ return getCurrentDevice().getDisplayMode(); }
	
	public static DisplayMode getDisplayModeFromCurrentDevice(int index){ return getCurrentDevice().getDisplayModes()[index]; }
	
	public static DisplayMode getDisplayModeFromDevice(int deviceIndex, int modeIndex){ return getDisplayModesForDevice(deviceIndex)[modeIndex]; }
	
	public static GraphicsDevice getCurrentDevice(){ return ENVIRONMENT.getDefaultScreenDevice(); }
	
	public static boolean headlessGraphics(){ return GraphicsEnvironment.isHeadless(); }
	
	public static boolean supportsDesktop(){ return Desktop.isDesktopSupported(); }
	
}
