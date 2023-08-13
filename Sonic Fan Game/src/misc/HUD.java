package misc;

import static java.lang.Math.*;

import datatypes.Animation;
import main.Loader;
import main.Window;
import player.Player;
import rendering.Camera;
import rendering.Image;
import rendering.Renderer;

public class HUD{
	
	public static final int FPS_SAMPLE_SIZE = 10;
	public static int SCALE = 2;
	private Image hud;
	private Image time;
	private Image[] numbers;
	private Image[] redNumbers;
	private Image start0;
	private Image start1;
	private Image start2;
	private Image start3;
	private Animation ring;
	public int rings;
	private float[] frames;
	private int numFrames;
	private int fps;
	private int voice;
	private int voiceTimer;
	private int ringTimer;
	private double timeFrames;
	
	public HUD(){
		hud = new Image(Loader.hud);
		time = new Image(Loader.time);
		start0 = new Image(Loader.start0);
		start1 = new Image(Loader.start1);
		start2 = new Image(Loader.start2);
		start3 = new Image(Loader.start3);
		numbers = new Image[Loader.numbers.length];
		for(int i = 0; i < numbers.length; i++) numbers[i] = new Image(Loader.numbers[i]);
		redNumbers = new Image[Loader.redNumbers.length];
		for(int i = 0; i < redNumbers.length; i++) redNumbers[i] = new Image(Loader.redNumbers[i]);
		ring = new Animation(Loader.hudRingAnim, new int[]{4, 4, 4, 4, 4, 4, 4, 4}, 0);
		hud.setPositions(1 * SCALE, 3 * SCALE, SCALE, SCALE);
		frames = new float[10];
		numFrames = fps = voice = 0;
		timeFrames = 0d;
	}
	
	public void manageAnimation(float dt, Player p){
		for(int f = 1; f < min(60.0f / (1.0f / dt), 5); f++){
			ring.update((p.vel.getLength() / 10 + 1));
			ringTimer++;
			if(ringTimer == 60) ringTimer = 0;
		}
	}
	
	public void updateTime(float dt){ timeFrames += dt * 1000000000; }
	
	public void draw(float dt, Player p, Camera camera, Renderer r){
		SCALE = Loader.scale;
		frames[numFrames] = dt;
		numFrames++;
		if(numFrames == FPS_SAMPLE_SIZE){
			float temp = 0;
			for(int i = 0; i < numFrames; i++) temp += frames[i];
			temp /= (numFrames * 1f);
			fps = (int)(1.0f / temp);
			numFrames = 0;
		}
		float xOffset = camera.position.x;
		float yOffset = camera.position.y + (Window.getInitHeight() - Window.getHeight());
		int screenWidth = Window.getWidth();
		int screenHeight = Window.getHeight();
		hud.setPositions(xOffset + 1 * SCALE, yOffset + 3 * SCALE, SCALE, SCALE);
		r.add(hud);
		ring.draw(xOffset + 7 * SCALE, yOffset + 8 * SCALE, SCALE, SCALE, r);
		if(p.rings > 0){
			drawNumber(28 * SCALE, 3 * SCALE, p.rings, 3, camera, r);
		}
		else{
			if(ringTimer < 30) drawNumber(28 * SCALE, 3 * SCALE, p.rings, 3, camera, r);
			else drawRedNumber(28 * SCALE, 3 * SCALE, p.rings, 3, camera, r);
		}
		drawNumber((1 + 27) * SCALE, (3 + 14) * SCALE, p.score, 6, camera, r);
		int ms = (int)(timeFrames / 10000000);
		int s = (int)(ms / 100);
		int m = (int)(s / 60);
		time.setPositions(xOffset + Window.getWidth() / 2 - time.getWidth() * SCALE / 2, yOffset + 3 * SCALE, SCALE, SCALE);
		r.add(time);
		drawNumber(Window.getWidth() / 2 - time.getWidth() * SCALE / 2 + 0 * SCALE, 3 * SCALE, m % 10, 1, camera, r);
		drawNumber(Window.getWidth() / 2 - time.getWidth() * SCALE / 2 + 16 * SCALE, 3 * SCALE, s % 60, 2, camera, r);
		drawNumber(Window.getWidth() / 2 - time.getWidth() * SCALE / 2 + 40 * SCALE, 3 * SCALE, ms % 100, 2, camera, r);
		drawNumber(Window.getWidth() - numbers[0].getWidth() * SCALE * 2 - 1 * SCALE, 3 * SCALE, fps, 2, camera, r);
		if(p.voice != 0){
			voice = p.voice;
			p.voice = 0;
		}
		if(voice == 3){
			start3.setPositions(xOffset + screenWidth / 2 - start3.getWidth() * SCALE / 2, yOffset + screenHeight / 2 - start3.getHeight() * SCALE / 2, SCALE, SCALE);
			r.add(start3);
		}
		if(voice == 2){
			start2.setPositions(xOffset + screenWidth / 2 - start2.getWidth() * SCALE / 2, yOffset + screenHeight / 2 - start2.getHeight() * SCALE / 2, SCALE, SCALE);
			r.add(start2);
		}
		if(voice == 1){
			start1.setPositions(xOffset + screenWidth / 2 - start1.getWidth() * SCALE / 2, yOffset + screenHeight / 2 - start1.getHeight() * SCALE / 2, SCALE, SCALE);
			r.add(start1);
		}
		if(voice == 4){
			start0.setPositions(xOffset + screenWidth / 2 - start0.getWidth() * SCALE / 2, yOffset + screenHeight / 2 - start0.getHeight() * SCALE / 2, SCALE, SCALE);
			r.add(start0);
			voiceTimer = 60 * 2;
			voice = 5;
		}
		if(voice == 5){
			start0.setPositions(xOffset + screenWidth / 2 - start0.getWidth() * SCALE / 2, yOffset + screenHeight / 2 - start0.getHeight() * SCALE / 2, SCALE, SCALE);
			r.add(start0);
			for(int f = 1; f < min(60.0f / (1.0f / dt), 5); f++){
				voiceTimer--;
				if(voiceTimer == 0) voice = 0;
			}
		}
	}
	
	private void drawNumber(int x, int y, int num, int places, Camera camera, Renderer r){
		float xOffset = camera.position.x;
		float yOffset = camera.position.y + (Window.getInitHeight() - Window.getHeight());
		for(int i = 0; i < places; i++){
			int n = (int)floor(num / pow(10, places - i - 1)) % 10;
			numbers[n].setPositions(xOffset + x + i * 8 * SCALE, yOffset + y, SCALE, SCALE);
			r.add(numbers[n]);
		}
	}
	
	private void drawRedNumber(int x, int y, int num, int places, Camera camera, Renderer r){
		float xOffset = camera.position.x;
		float yOffset = camera.position.y + (Window.getInitHeight() - Window.getHeight());
		for(int i = 0; i < places; i++){
			int n = (int)floor(num / pow(10, places - i - 1)) % 10;
			redNumbers[n].setPositions(xOffset + x + i * 8 * SCALE, yOffset + y, SCALE, SCALE);
			r.add(redNumbers[n]);
		}
	}
	
}
