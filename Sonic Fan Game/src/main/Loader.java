package main;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.lwjgl.BufferUtils;

import rendering.Image;
import scenes.Scene;

public class Loader{
	
	private static Loader singleton = null;
	public static final String TITLE = "Sonic Advance 2 DX";
	public static Scene currentScene;
	public static boolean fullscreen;
	public static int joyUp = 16;
	public static int joyDown = 18;
	public static int joyLeft = 19;
	public static int joyRight = 17;
	public static int joyA = 0;
	public static int joyB = 1;
	public static int joyX = 2;
	public static int joyY = 3;
	public static int joyStart = 8;
	public static int joyBack = 9;
	public static int joyUpAxis = 0;
	public static int joyDownAxis = 0;
	public static int joyLeftAxis = 0;
	public static int joyRightAxis = 0;
	public static int graphicsWidth;
	public static int graphicsHeight;
	public static int fps = 60;
	public static int scale = 2;
	public static int width = 240 * 4;
	public static int height = 160 * 4;
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
	public static ByteBuffer[] skirtAnim;
	public static ByteBuffer[] turnAnim;
	public static ByteBuffer[] landAnim;
	public static ByteBuffer[] startAnim;
	public static ByteBuffer[] trickRightAnim;
	public static ByteBuffer[] trickUp0Anim;
	public static ByteBuffer[] trickUp1Anim;
	public static ByteBuffer[] rampAnim;
	public static ByteBuffer[] sonicRotorAnim;
	public static ByteBuffer[] dashAnim;
	public static ByteBuffer[] doubleSpinAnim;
	public static ByteBuffer[] slideAnim;
	public static ByteBuffer[] smashStartAnim;
	public static ByteBuffer[] smashEndAnim;
	public static ByteBuffer[] backflipAnim;
	public static ByteBuffer[] helixAnim;
	public static ByteBuffer[] grindAnim;
	public static ByteBuffer[] spindashDustAnim;
	public static ByteBuffer[] spindashChargeDustAnim;
	public static ByteBuffer[] doubleShieldAnim;
	public static ByteBuffer[] spring0Anim;
	public static ByteBuffer[] spring1Anim;
	public static ByteBuffer[] spring2Anim;
	public static ByteBuffer[] spring3Anim;
	public static ByteBuffer[] spring4Anim;
	public static ByteBuffer[] spring5Anim;
	public static ByteBuffer[] spring6Anim;
	public static ByteBuffer[] spring7Anim;
	public static ByteBuffer[] spring8Anim;
	public static ByteBuffer[] spring9Anim;
	public static ByteBuffer[] spring10Anim;
	public static ByteBuffer[] spring11Anim;
	public static ByteBuffer[] blueSpring0Anim;
	public static ByteBuffer[] blueSpring1Anim;
	public static ByteBuffer[] dashPad;
	public static ByteBuffer[] ringAnim;
	public static ByteBuffer[] sparkleAnim;
	public static ByteBuffer[] rotorAnim;
	public static ByteBuffer[] springPoleFastAnim;
	public static ByteBuffer[] springPoleSlowAnim;
	public static ByteBuffer leafBG0;
	public static ByteBuffer leafBG1;
	public static ByteBuffer leafBG2;
	public static ByteBuffer windowIcon2;
	public static ByteBuffer windowIcon3;
	public static ByteBuffer start0;
	public static ByteBuffer start1;
	public static ByteBuffer start2;
	public static ByteBuffer start3;
	public static ByteBuffer pause1;
	public static ByteBuffer pause2;
	public static ByteBuffer pause3;
	public static ByteBuffer font;
	public static ByteBuffer singleplayerWhiteSprite;
	public static ByteBuffer singleplayerYellowSprite;
	public static ByteBuffer multiplayerWhiteSprite;
	public static ByteBuffer multiplayerYellowSprite;
	public static ByteBuffer gameStartWhiteSprite;
	public static ByteBuffer gameStartYellowSprite;
	public static ByteBuffer timeAttackWhiteSprite;
	public static ByteBuffer timeAttackYellowSprite;
	public static ByteBuffer optionsWhiteSprite;
	public static ByteBuffer optionsYellowSprite;
	public static ByteBuffer leafLayer1;
	public static ByteBuffer leafLayer2;
	public static ByteBuffer[] hudRingAnim;
	public static ByteBuffer hud;
	public static ByteBuffer time;
	public static ByteBuffer[] numbers;
	public static ByteBuffer[] redNumbers;
	public static ByteBuffer itemBox;
	public static ByteBuffer ramp;
	public static ByteBuffer fade;
	public static ByteBuffer title;
	public static ByteBuffer leftCloud;
	public static ByteBuffer rightCloud;
	public static ByteBuffer pressStart;
	public static ByteBuffer[] spinnerAnim;
	public static ByteBuffer[] explosionAnim;
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
	public static Clip trickSound;
	public static Clip boostSound;
	public static Clip dashSound;
	public static Clip voice3;
	public static Clip voice2;
	public static Clip voice1;
	public static Clip voiceGo;
	public static Clip ringSound;
	public static Clip springSound;
	public static Clip popSound;
	public static Clip springPoleSound;
	public static Clip leaf1Music;
	public static Clip titleScreenMusic;
	public static Clip pauseSound;
	public static Clip titleSound;
	public static Clip forwardSound;
	public static Clip backSound;
	public static Clip moveSound;
	public static Clip inaccessibleSound;
	public static int debugMode;
	
	public static void main(String[] args){
		if(args.length > 0) if(args[0].equals("debug")) if(args[1].equals("leaf1")){
			debugMode = 1;
			joyUp = 1;
			joyDown = 1;
			joyLeft = 0;
			joyRight = 0;
			joyA = 0;
			joyB = 1;
			joyX = 2;
			joyY = 3;
			joyStart = 6;
			joyBack = 7;
			joyUpAxis = -1;
			joyDownAxis = 1;
			joyLeftAxis = -1;
			joyRightAxis = 1;
		}
		get().init();
		Window.get().run();
	}
	
	public static Loader get(){
		if(singleton == null) singleton = new Loader();
		return singleton;
	}
	
	private void init(){
		fade = loadImage("/objectsprites/fade.png");
		title = loadImage("/objectsprites/title.png");
		leftCloud = loadImage("/objectsprites/cloudLeft.png");
		rightCloud = loadImage("/objectsprites/cloudRight.png");
		pressStart = loadImage("/objectsprites/start.png");
		idleAnim = loadImages("/sonicsprites", "idle");
		runSlowestAnim = loadImages("/sonicsprites", "slowest");
		runSlowAnim = loadImages("/sonicsprites", "slow");
		runNormalAnim = loadImages("/sonicsprites", "run");
		runFastAnim = loadImages("/sonicsprites", "fast");
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
		skirtAnim = loadImages("/sonicsprites", "skirt");
		turnAnim = loadImages("/sonicsprites", "turn");
		landAnim = loadImages("/sonicsprites", "land");
		startAnim = loadImages("/sonicsprites", "start");
		trickRightAnim = loadImages("/sonicsprites", "whirl");
		trickUp0Anim = loadImages("/sonicsprites", "trickUp0");
		trickUp1Anim = loadImages("/sonicsprites", "trickUp1");
		rampAnim = loadImages("/sonicsprites", "ramp");
		sonicRotorAnim = loadImages("/sonicsprites", "sonicRotor");
		dashAnim = loadImages("/sonicsprites", "dash");
		doubleSpinAnim = loadImages("/sonicsprites", "doubleSpin");
		slideAnim = loadImages("/sonicsprites", "slide");
		smashStartAnim = loadImages("/sonicsprites", "smashStart");
		smashEndAnim = loadImages("/sonicsprites", "smashEnd");
		backflipAnim = loadImages("/sonicsprites", "backFlip");
		helixAnim = loadImages("/sonicsprites", "helix");
		grindAnim = loadImages("/sonicsprites", "grind");
		spindashDustAnim = loadImages("/sonicsprites", "dust");
		spindashChargeDustAnim = loadImages("/sonicsprites", "chargeDust");
		doubleShieldAnim = loadImages("/sonicsprites", "doubleShield");
		spring0Anim = loadImages("/objectsprites", "spring0");
		spring1Anim = loadImages("/objectsprites", "spring1");
		spring2Anim = loadImages("/objectsprites", "spring2");
		spring3Anim = loadImages("/objectsprites", "spring3");
		spring4Anim = loadImages("/objectsprites", "spring4");
		spring5Anim = loadImages("/objectsprites", "spring5");
		spring6Anim = loadImages("/objectsprites", "spring6");
		spring7Anim = loadImages("/objectsprites", "spring7");
		spring8Anim = loadImages("/objectsprites", "spring8");
		spring9Anim = loadImages("/objectsprites", "spring9");
		spring10Anim = loadImages("/objectsprites", "spring10");
		spring11Anim = loadImages("/objectsprites", "spring11");
		blueSpring0Anim = loadImages("/objectsprites", "blueSpring0");
		blueSpring1Anim = loadImages("/objectsprites", "blueSpring1");
		dashPad = loadImages("/objectsprites", "dashPad");
		itemBox = loadImage("/objectsprites/itemBox.png");
		ramp = loadImage("/objectsprites/ramp.png");
		ringAnim = loadImages("/hudsprites", "ring");
		sparkleAnim = loadImages("/objectsprites", "effect");
		rotorAnim = loadImages("/objectsprites", "rotor");
		springPoleFastAnim = loadImages("/objectsprites", "springPole");
		springPoleSlowAnim = loadImages("/objectsprites", "springPoleSlow");
		jumpSound0 = loadSound("/sonicsounds/jump0.wav", -15.0f);
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
		trickSound = loadSound("/sonicsounds/trick.wav", -10.0f);
		boostSound = loadSound("/sonicsounds/boost.wav", -10.0f);
		dashSound = loadSound("/sonicsounds/dash.wav", -10.0f);
		voice3 = loadSound("/voiceclips/3.wav", -10.0f);
		voice2 = loadSound("/voiceclips/2.wav", -10.0f);
		voice1 = loadSound("/voiceclips/1.wav", -10.0f);
		voiceGo = loadSound("/voiceclips/go.wav", -10.0f);
		titleScreenMusic = loadSound("/objectsounds/titleScreen.wav", -20.0f);
		leaf1Music = loadSound("/maps/leaf1.wav", -20.0f);
		pauseSound = loadSound("/objectsounds/pause.wav", -10.0f);
		titleSound = loadSound("/objectsounds/title.wav", -10.0f);
		forwardSound = loadSound("/objectsounds/forward.wav", -10.0f);
		backSound = loadSound("/objectsounds/back.wav", -10.0f);
		moveSound = loadSound("/objectsounds/move.wav", -10.0f);
		inaccessibleSound = loadSound("/objectsounds/inaccessible.wav", -10.0f);
		ringSound = loadSound("/objectsounds/ring.wav", -10.0f);
		springSound = loadSound("/objectsounds/spring.wav", -10.0f);
		popSound = loadSound("/objectsounds/pop.wav", -10.0f);
		springPoleSound = loadSound("/objectsounds/springPole.wav", -10.0f);
		hudRingAnim = loadImages("/hudsprites", "ring");
		leafBG0 = loadImage("/maps/bg20.png");
		leafBG1 = loadImage("/maps/bg21.png");
		leafBG2 = loadImage("/maps/bg22.png");
		singleplayerWhiteSprite = loadImage("/menusprites/singleplayerWhite.png");
		singleplayerYellowSprite = loadImage("/menusprites/singleplayerYellow.png");
		multiplayerWhiteSprite = loadImage("/menusprites/multiplayerWhite.png");
		multiplayerYellowSprite = loadImage("/menusprites/multiplayerYellow.png");
		gameStartWhiteSprite = loadImage("/menusprites/gameStartWhite.png");
		gameStartYellowSprite = loadImage("/menusprites/gameStartYellow.png");
		timeAttackWhiteSprite = loadImage("/menusprites/timeAttackWhite.png");
		timeAttackYellowSprite = loadImage("/menusprites/timeAttackYellow.png");
		optionsWhiteSprite = loadImage("/menusprites/optionsWhite.png");
		optionsYellowSprite = loadImage("/menusprites/optionsYellow.png");
		windowIcon2 = loadImage("/objectsprites/windowIcon2.png");
		windowIcon3 = loadImage("/objectsprites/windowIcon3.png");
		font = loadImage("/menusprites/font.png");
		start0 = loadImage("/hudsprites/start.png");
		start1 = loadImage("/hudsprites/_1.png");
		start2 = loadImage("/hudsprites/_2.png");
		start3 = loadImage("/hudsprites/_3.png");
		pause1 = loadImage("/hudsprites/pause1.png");
		pause2 = loadImage("/hudsprites/pause2.png");
		pause3 = loadImage("/hudsprites/pause3.png");
		leafLayer1 = loadImage("/maps/Leaf_Forest_Act_1.png");
		leafLayer2 = loadImage("/maps/Leaf_Forest_Act_1.png");
		hud = loadImage("/hudsprites/rings.png");
		time = loadImage("/hudsprites/time.png");
		numbers = loadImages("/hudsprites", "");
		redNumbers = loadImages("/hudsprites", "red");
		spinnerAnim = loadImages("/badniksprites", "spinner");
		explosionAnim = loadImages("/objectsprites", "explosion");
	}
	
	private ByteBuffer[] loadImages(String dir, String name){
		int length = 0;
		while(true){
			InputStream is = getClass().getResourceAsStream(dir + "/" + name + length + ".png");
			if(is == null) break;
			length++;
		}
		ByteBuffer[] images = new ByteBuffer[length];
		try{
			for(int i = 0; i < length; i++){
				InputStream is = getClass().getResourceAsStream(dir + "/" + name + i + ".png");
				byte[] bytes = new byte[is.available()];
				is.read(bytes);
				ByteBuffer imageBuffer = BufferUtils.createByteBuffer(bytes.length);
				imageBuffer.put(bytes);
				imageBuffer.flip();
				images[i] = imageBuffer;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return images;
	}
	
	public ByteBuffer loadImage(String path){
		ByteBuffer out = null;
		try{
			InputStream is = getClass().getResourceAsStream(path);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			ByteBuffer imageBuffer = BufferUtils.createByteBuffer(bytes.length);
			imageBuffer.put(bytes);
			imageBuffer.flip();
			out = imageBuffer;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return out;
	}
	
	private Clip loadSound(String path, float amp){
		Clip sound = null;
		try{
			InputStream is = getClass().getResourceAsStream(path);
			BufferedInputStream bis = new BufferedInputStream(is);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
			sound = AudioSystem.getClip();
			sound.open(ais);
		}
		catch(Exception ex){
			System.err.println("Faied to load sound '" + path + "'");
			ex.printStackTrace();
		}
		FloatControl gainControl = (FloatControl)sound.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(amp);
		return sound;
	}
	
}
