package com.sulvic_myshicalatlas.sa2dx.client;

import static com.sulvic.glfw.GLFWHelper.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.sulvic_myshicalatlas.sa2dx.util.AssetLoader;

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

	private void init(){
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()) throw new IllegalStateException("Unable to initualize GLFW.");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		// glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		// glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		windowId = glfwCreateWindow(gameWidth, gameHeight, gameTitle, NULL, NULL);
		if(windowId == NULL) throw new RuntimeException("Failed to create the GLFW window.");
		glfwSetWindowIcon(windowId, AssetLoader.getIcon());
		glfwCenterWindow(windowId);
		glfwMakeContextCurrent(windowId);
		glfwSwapInterval(1);
		createCapabilities();
		glClearColor(0f, 0f, 0f, 1f);
		glfwShowWindow(windowId);
	}

	public int getHeight(){ return gameHeight; }

	public int getWidth(){ return gameWidth; }

	public String getTitle(){ return gameTitle; }

	public void run(){
		init();
		while(!glfwWindowShouldClose(windowId)){
			glfwPollEvents();
			glClear(GL_COLOR_BUFFER_BIT);
			glfwSwapBuffers(windowId);
		}
		glfwTerminate();
	}

}
