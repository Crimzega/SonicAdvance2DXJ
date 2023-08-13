package com.sulvic_myshicalatlas.sa2dx.client;

import static com.sulvic.glfw.GLFWHelper.*;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.sulvic_myshicalatlas.sa2dx.client.scene.SceneHandler;
import com.sulvic_myshicalatlas.sa2dx.util.AssetLoader;

import org.lwjgl.glfw.GLFWErrorCallback;

public class GameWindow{
	
	protected static long windowId;
	private int gameHeight, gameWidth;
	private String gameTitle;
	
	public GameWindow(String title, int width, int height){
		gameTitle = title;
		gameWidth = width;
		gameHeight = height;
	}
	
	public static long getWindow(){ return windowId; }
	
	private void run(){
		float delta = -1f;
		while(!glfwWindowShouldClose(windowId)){
			glClearColor(0f, 0f, 0f, 1f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			SceneHandler.tickScene(delta);
			SceneHandler.renderScene(delta);
			glfwPollEvents();
			glfwSwapBuffers(windowId);
		}
	}
	
	public int getHeight(){ return gameHeight; }
	
	public int getWidth(){ return gameWidth; }
	
	public String getTitle(){ return gameTitle; }
	
	public void build(){
		GLFWErrorCallback.createPrint(System.out).set();
		if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
		windowId = glfwCreateWindow(gameWidth, gameHeight, gameTitle, NULL, NULL);
		if(windowId == NULL) throw new IllegalStateException("Failed to create the GLFW window");
		glfwSetWindowIcon(windowId, AssetLoader.getIcon());
		glfwMakeContextCurrent(windowId);
		createCapabilities();
		glfwCenterWindow(windowId);
		glfwShowWindow(windowId);
		glfwSwapInterval(1);
		run();
	}
	
}
