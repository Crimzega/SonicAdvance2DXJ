package objects;

import datatypes.Animation;
import datatypes.Vector;
import main.Loader;
import rendering.Renderer;

public class Rotor{
	
	public int facing;
	public Vector pos;
	public Animation anim;
	
	public Rotor(double x, double y){
		pos = new Vector(x, y);
		anim = new Animation(Loader.rotorAnim, new int[]{4, 4, 4, 4, 4, 4}, 0);
		facing = 1;
	}
	
	public void draw(int scaleX, int scaleY, float dt, Renderer r){
		anim.draw((pos.x - anim.getCurrentWidth()) / 2 * Loader.scale, (pos.y - anim.getCurrentHeight()) / 2 * Loader.scale, Loader.scale * facing, Loader.scale, r);
	}
	
}
