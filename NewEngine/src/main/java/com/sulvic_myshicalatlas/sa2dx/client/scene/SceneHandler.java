package com.sulvic_myshicalatlas.sa2dx.client.scene;

import java.util.Map;

import com.sulvic.util.ContentBuilder;

public class SceneHandler{
	
	private static final Map<String, IScene> CUSTOM_SCENES = ContentBuilder.newHashMap();
	private static String currentScene = "game";
	
	static{
		CUSTOM_SCENES.put("game", new GameScene());
	}
	
	private static IScene getCurrentScene(){ return CUSTOM_SCENES.get(currentScene); }
	
	public static void setScene(String name){
		if(currentScene.equals(name)) return;
		currentScene = name;
	}
	
	public static void renderScene(float delta){ getCurrentScene().render(delta); }
	
	public static void tickScene(float delta){ getCurrentScene().tick(delta); }
	
}
