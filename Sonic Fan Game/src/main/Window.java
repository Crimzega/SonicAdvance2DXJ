package main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import scenes.MainScene;
import scenes.MenuScene;
import scenes.Scene;

public class Window{
	
	private long glfwWindow;
	private static Window window = null;
	private Scene currentScene = null;
	private int initWidth;
	private int initHeight;
	
	private Window(){}
	
	public static Window get(){
		if(window == null) window = new Window();
		return window;
	}
	
	public static void changeScene(int newScene){
		switch(newScene){
			case (0):
				get().currentScene = new MenuScene();
				get().currentScene.init();
			break;
			case (1):
				get().currentScene = new MainScene();
				get().currentScene.init();
			break;
			default:
				assert false: "Unknown scene " + newScene + ".";
		}
	}
	
	public static Scene getScene(){ return get().currentScene; }
	
	public void run(){
		init();
		loop();
	}
	
	public void init(){
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
		glfwWindowHint(GLFW_DOUBLEBUFFER, GL_FALSE);
		glfwWindow = glfwCreateWindow(Loader.width, Loader.height, Loader.TITLE, NULL, NULL);
		if(glfwWindow == NULL) throw new IllegalStateException("Failed to create the GLFW window.");
		glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
		glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
		glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
		glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
		glfwMakeContextCurrent(glfwWindow);
		glfwSwapInterval(1);
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer imageBuffer = stbi_load_from_memory(Loader.windowIcon3, width, height, channels, 0);
		GLFWImage image = GLFWImage.create();
		GLFWImage.Buffer imagebf = GLFWImage.create(1);
		image.set(width.get(0), height.get(0), imageBuffer);
		imagebf.put(0, image);
		glfwSetWindowIcon(glfwWindow, imagebf);
		GL.createCapabilities();
		initWidth = getWidth();
		initHeight = getHeight();
		if(Loader.debugMode == 0) changeScene(0);
		if(Loader.debugMode == 1) changeScene(1);
		glfwRestoreWindow(glfwWindow);
		setWidth(Loader.width);
		setHeight(Loader.height);
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(glfwWindow, (vidmode.width() - getWindowWidth()) / 2, (vidmode.height() - getWindowHeight()) / 2);
		glfwShowWindow(glfwWindow);
	}
	
	public void loop(){
		float beginTime = Time.getTime();
		float endTime = Time.getTime();
		float dt = -1.0f;
		boolean first = true;
		while(!glfwWindowShouldClose(glfwWindow)){
			if(Time.getTime() - beginTime >= 1.0f / (Loader.fps * 1.0f) || first){
				dt = Time.getTime() - beginTime;
				beginTime = Time.getTime();
				glfwPollEvents();
				glClearColor(0, 0, 0, 1);
				glClear(GL_COLOR_BUFFER_BIT);
				if(!first) currentScene.update(dt);
				glFlush();
				first = false;
			}
		}
	}
	
	public static int getInitWidth(){ return get().initWidth; }
	
	public static int getInitHeight(){ return get().initHeight; }
	
	public static int getWidth(){
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(get().glfwWindow, w, h);
		int width = w.get(0);
		return width;
	}
	
	public static int getHeight(){
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(get().glfwWindow, w, h);
		int height = h.get(0);
		return height;
	}
	
	public static void setWidth(int width){
		glfwSetWindowSize(getWindow(), width, getHeight());
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(getWindow(), (vidmode.width() - getWindowWidth()) / 2, (vidmode.height() - getWindowHeight()) / 2);
	}
	
	public static void setHeight(int height){
		glfwSetWindowSize(getWindow(), getWidth(), height);
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(getWindow(), (vidmode.width() - getWindowWidth()) / 2, (vidmode.height() - getWindowHeight()) / 2);
	}
	
	public static int getWindowWidth(){
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(get().glfwWindow, w, h);
		int width = w.get(0);
		return width;
	}
	
	public static int getWindowHeight(){
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(get().glfwWindow, w, h);
		int height = h.get(0);
		return height;
	}
	
	public static long getWindow(){ return get().glfwWindow; }
	
}
