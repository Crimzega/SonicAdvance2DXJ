package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.lwjgl.BufferUtils;

import datatypes.Animation;
import datatypes.Scene;
import datatypes.TiledJSON;
import datatypes.Tilemap;
import misc.Background;
import misc.HUD;
import rendering.Image;
import rendering.Shader;

import static functionholders.GraphicsFunctions.*;

public class Loader {
	private static Loader singleton = null;
	
	public static final String TITLE = "Sonic Fan Game";
	public static final int TARGET_FPS = 60;
	public static final int SCALE = 1;
	public static final int DEFAULT_FRAME_WIDTH = 1280;
	public static final int DEFAULT_FRAME_HEIGHT = 720;
	public static boolean loadedAssets;
	
	public static Scene currentScene;
	public static boolean fullscreen;
	
	public static int graphicsWidth;
	public static int graphicsHeight;
	
	public static int[][] testMap1;
	public static int[][] testMap2;
	
	public static ByteBuffer[] idleAnim;
	public static ByteBuffer[] runSlowestAnim;
	public static ByteBuffer[] runSlowAnim;
	public static ByteBuffer[] runNormalAnim;
	public static ByteBuffer[] runFastAnim;
	public static ByteBuffer[] runFastestAnim;
	public static ByteBuffer[] bounceUpAnim;
	public static ByteBuffer[] bounceDownAnim;
	public static ByteBuffer[] fallAnim;
	public static ByteBuffer[] jumpAnim;
	public static ByteBuffer[] skidAnim;
	public static ByteBuffer[] spinAnim;
	public static ByteBuffer[] crouchAnim0;
	public static ByteBuffer[] crouchAnim1;
	public static ByteBuffer[] spindashAnim;
	public static ByteBuffer[] spindashChargeAnim;
	public static ByteBuffer[] spindashDustAnim;
	public static ByteBuffer[] spindashChargeDustAnim;
	public static ByteBuffer[] skirtAnim;
	public static ByteBuffer[] turnAnim;
	
	public static ByteBuffer[] springAnim;
	public static ByteBuffer[] ringAnim;
	public static ByteBuffer[] sparkleAnim;
	
	public static ByteBuffer leafBG0;
	public static ByteBuffer leafBG1;
	public static ByteBuffer leafBG2;
	
	public static ByteBuffer leafLayer1;
	public static ByteBuffer leafLayer2;
	
	public static ByteBuffer[] hudRingAnim;
	public static ByteBuffer hud;
	public static ByteBuffer time;
	public static ByteBuffer[] numbers;
	
	public static Image testBuffer;
	
	public static Clip jumpSound0;
	public static Clip jumpSound1;
	public static Clip landSound;
	public static Clip skidSound;
	public static Clip spinSound;
	public static Clip spindashChargeSound;
	public static Clip spindashReleaseSound;
	public static Clip stepSound0;
	public static Clip stepSound1;
	public static Clip stepSound2;
	public static Clip stepSound3;
	public static Clip stepSound4;
	
	public static Clip ringSound;
	public static Clip springSound;
	
	public static void main(String[] args) {
		get().init();
		Window.get().run();
	}

	public static Loader get() {
		if(singleton == null) {singleton = new Loader();}
		return(singleton);
	}
	
	private Loader() {}
	
	public void init() {
		if(!loadedAssets) {
			loadedAssets = true;
			
			testMap1 = new TiledJSON("/maps/testMap1.json").map[0];
			testMap2 = new TiledJSON("/maps/testMap2.json").map[0];
			
			idleAnim = loadImages("/sonicsprites", "idle");
			runSlowestAnim = loadImages("/sonicsprites", "slowest");
			runSlowAnim    = loadImages("/sonicsprites", "slow");
			runNormalAnim  = loadImages("/sonicsprites", "run");
			runFastAnim    = loadImages("/sonicsprites", "fast");
			runFastestAnim = loadImages("/sonicsprites", "fastest");
			bounceUpAnim = loadImages("/sonicsprites", "bounceUp");
			bounceDownAnim = loadImages("/sonicsprites", "bounceDown");
			fallAnim = loadImages("/sonicsprites", "fall");
			jumpAnim = loadImages("/sonicsprites", "jump");
			skidAnim = loadImages("/sonicsprites", "skid");
			spinAnim = loadImages("/sonicsprites", "spin");
			crouchAnim0 = loadImages("/sonicsprites", "crouchDown");
			crouchAnim1 = loadImages("/sonicsprites", "crouchUp");
			spindashAnim = loadImages("/sonicsprites", "spindash");
			spindashChargeAnim = loadImages("/sonicsprites", "charge");
			spindashDustAnim = loadImages("/sonicsprites", "dust");
			spindashChargeDustAnim = loadImages("/sonicsprites", "chargeDust");
			skirtAnim = loadImages("/sonicsprites", "skirt");
			turnAnim = loadImages("/sonicsprites", "turn");
			
			springAnim = loadImages("/objectsprites", "spring");
			ringAnim = loadImages("/hudsprites", "ring");
			sparkleAnim = loadImages("/objectsprites", "effect");
			
			jumpSound0 = loadSound("/sonicsounds/jump0.wav", -10.0f);
			jumpSound1 = loadSound("/sonicsounds/jump1.wav", -10.0f);
			landSound = loadSound("/sonicsounds/land.wav", -10.0f);
			skidSound = loadSound("/sonicsounds/skid.wav", -10.0f);
			spinSound = loadSound("/sonicsounds/spin.wav", -10.0f);
			spindashChargeSound = loadSound("/sonicsounds/spindashCharge.wav", -10.0f);
			spindashReleaseSound = loadSound("/sonicsounds/spindashRelease.wav", -10.0f);
			stepSound0 = loadSound("/sonicsounds/step0.wav", -10.0f);
			stepSound1 = loadSound("/sonicsounds/step1.wav", -10.0f);
			stepSound2 = loadSound("/sonicsounds/step2.wav", -10.0f);
			stepSound3 = loadSound("/sonicsounds/step3.wav", -10.0f);
			stepSound4 = loadSound("/sonicsounds/step4.wav", -10.0f);
			
			ringSound = loadSound("/objectsounds/ring.wav", -20.0f);
			springSound = loadSound("/objectsounds/spring.wav", -20.0f);
			
			hudRingAnim = loadImages("/hudsprites", "ring");
			
			leafBG0 = loadImage("/maps/bg20.png");
			leafBG1 = loadImage("/maps/bg21.png");
			leafBG2 = loadImage("/maps/bg22.png");
			
			leafLayer1 = loadImage("/maps/Leaf_Forest_Act_1.png");
			leafLayer2 = loadImage("/maps/Leaf_Forest_Act_1.png");
			
			hud = loadImage("/hudsprites/rings.png");
			time = loadImage("/hudsprites/time.png");
				
			numbers = loadImages("/hudsprites", "");
		}
	}
	
	private ByteBuffer[] loadImages(String dir, String name) {
		int length = 0;
		
		while(true) {
			InputStream is = getClass().getResourceAsStream(dir + "/" + name + length + ".png");
			if(is == null) {break;}
			
			length++;
		}
		
		ByteBuffer[] images = new ByteBuffer[length];
		
		try {
			for(int i = 0; i < length; i++) {
				InputStream is = getClass().getResourceAsStream(dir + "/" + name + i + ".png");
				byte[] bytes = is.readAllBytes();
				
				ByteBuffer imageBuffer = BufferUtils.createByteBuffer(bytes.length);
				imageBuffer.put(bytes);
				imageBuffer.flip();
				
				images[i] = imageBuffer;
			}
		}
		catch(Exception e) {e.printStackTrace();}
		
		return(images);
	}
	public ByteBuffer loadImage(String path) {
		ByteBuffer out = null;
		
		try {
			InputStream is = getClass().getResourceAsStream(path);
			byte[] bytes = is.readAllBytes();
			
			ByteBuffer imageBuffer = BufferUtils.createByteBuffer(bytes.length);
			imageBuffer.put(bytes);
			imageBuffer.flip();
			
			out = imageBuffer;
		}
		catch(Exception e) {e.printStackTrace();}
		
		return(out);
	}
	
	private Clip loadSound(String path, float amp) {
		Clip sound = null;
		try {
			InputStream is = getClass().getResourceAsStream(path);
			BufferedInputStream bis = new BufferedInputStream(is);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
			
			sound = AudioSystem.getClip();
			sound.open(ais);
		}
		catch (Exception e) {
			System.err.println("Faied to load sound '" + path + "'");
			e.printStackTrace();
		}
		FloatControl gainControl = (FloatControl)sound.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(amp);
		
		return(sound);
	}
}