package com.sulvic_myshicalatlas.sa2dx.client.texture;

import static com.sulvic_myshicalatlas.sa2dx.client.renderer.ImageHandler.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.util.List;

import com.sulvic.util.ContentBuilder;

public class CharacterImage{
	
//	private List<BufferedImage> charFrames = ContentBuilder.newArrayList();
	private List<Integer> frameTextureIds = ContentBuilder.newArrayList(), frameTimeouts = ContentBuilder.newArrayList();
	private double animSpeed;
	private int currFrame, restartFrame = 0;
	private int textureId;
	
	private CharacterImage(List<BufferedImage> frames, int... timeouts){
		for(BufferedImage img: frames) frameTextureIds.add(toTexture(img));
		for(int timeout: timeouts) frameTimeouts.add(timeout);
	}
	
	public void render(float delta){
		renderImage(0f, 0f, frameTextureIds.get(currFrame));
	}
	
	public void tick(float delta){
		
	}
	
	public CharacterImage setRepeatFrame(int frame){
		restartFrame = frame;
		return this;
	}
	
	public int getCurrentImage(){ return frameTextureIds.get(currFrame); }
	
	public static CharacterImage newCharacterImage(List<BufferedImage> images, int... frameTimeouts){ return new CharacterImage(images, frameTimeouts); }
	
}
