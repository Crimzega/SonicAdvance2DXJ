package badniks;

import datatypes.Animation;
import datatypes.Vector;
import main.Loader;
import rendering.Camera;
import rendering.Renderer;
import rendering.Shader;

public class Spinner extends Badnik{
	
	private Animation anim;
	
	public Spinner(double x, double y){
		pos = new Vector(x, y);
		anim = new Animation(Loader.spinnerAnim, new int[]{2, 2, 2, 2, 2, 2}, 0);
		destroy = 0;
	}
	
	public void update(float dt){}
	
	public void manageAnimation(float dt){
		for(int f = 1; f < 60.0f / (1.0f / dt); f++){ anim.update(1); }
		if(destroy == 1 && anim.finished){ destroy = 2; }
	}
	
	public void draw(int scaleX, int scaleY, float dt, Renderer r){ anim.draw(pos.x, pos.y, scaleX, scaleY, r); }
	
	public void destroy(){
		int initWidth = anim.getCurrentSize()[0];
		int initHeight = anim.getCurrentSize()[1];
		
		destroy = 1;
		anim = new Animation(Loader.explosionAnim, new int[]{6, 6, 6, 7}, 0);
		
		int newWidth = anim.getCurrentSize()[0];
		int newHeight = anim.getCurrentSize()[1];
		
		pos.x -= (newWidth - initWidth) / 2;
		pos.y -= (newHeight - initHeight) / 2;
	}

}
