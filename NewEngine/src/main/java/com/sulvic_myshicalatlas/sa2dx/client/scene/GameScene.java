package com.sulvic_myshicalatlas.sa2dx.client.scene;

import com.sulvic.engine.util.AssetLocation;
import com.sulvic_myshicalatlas.sa2dx.client.texture.CharacterImage;
import com.sulvic_myshicalatlas.sa2dx.util.AssetLoader;

public class GameScene implements IScene{
	
	private static CharacterImage sonicIdle = AssetLoader.getCharacterImage(new AssetLocation("sa2dx", "sonic"), "idle");
	
	public void render(float delta){ sonicIdle.render(delta); }
	
	public void tick(float delta){ sonicIdle.tick(delta); }
	
}
