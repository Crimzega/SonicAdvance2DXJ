package player;

import static functionholders.CollisionFunctions.*;
import static java.lang.Math.*;

import static player.PlayerConstants.*;
import static player.PlayerSounds.*;

import java.awt.Color;

import badniks.Badnik;
import datatypes.Shape;
import datatypes.Vector;
import objects.BlueSpring;
import objects.DashPad;
import objects.Helix;
import objects.Item;
import objects.Rail;
import objects.Ramp;
import objects.Ring;
import objects.Rotor;
import objects.Spring;
import objects.SpringPole;
import shapes.Circle;
import shapes.Rectangle;

public class PlayerObjectHandling{
	
	public static void rings(Player p, Ring[] rings){
		if(rings != null){
			for(int i = 0; i < rings.length; i++){
				if(rings[i].destroy == 0){
					Shape ringMask = new Circle(rings[i].pos, 8 * 2);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, ringMask)){
						p.rings++;
						rings[i].destroy = 1;
						p.ps.playSound(SOUND_RING);
					}
				}
			}
		}
	}
	
	public static void badniks(Player p, Badnik[] badniks){
		if(badniks != null){
			for(int i = 0; i < badniks.length; i++){
				if(badniks[i].destroy == 0){
					Shape badnikMask = new Circle(badniks[i].pos.add(8 * 2, 4 * 2), 8 * 2);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, badnikMask)){
						if(p.state == STATE_SPINNING || p.state == STATE_SPINDASHING || (p.anim == JUMP_ANIM || p.anim == LAND_ANIM)){
							p.score += 100;
							if(!p.ground){
								p.vel = p.vel.project(new Vector(1, 0));
								p.vel.translate(new Vector(0, -1).scale(10));
							}
							badniks[i].destroy();
							p.ps.playSound(SOUND_POP);
						}
					}
				}
			}
		}
	}
	
	public static void items(Player p, Item[] items){
		if(items != null){
			for(int i = 0; i < items.length; i++){
				if(items[i].destroy == 0){
					Shape itemMask = new Circle(items[i].pos.add(8 * 2, 4 * 2), 8 * 2);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, itemMask)){
						p.vel = p.vel.project(new Vector(1, 0));
						p.vel.translate(new Vector(0, -1).scale(5));
						items[i].destroy();
						p.jumpReady = p.ground = p.jumpingUp = p.stopCam = false;
						p.state = STATE_BOUNCING;
						p.bounceType = 0;
						p.ps.playSound(SOUND_POP);
					}
				}
			}
		}
	}
	
	public static void springs(Player p, Spring[] springs){
		if(springs != null){
			for(int i = 0; i < springs.length; i++){
				if(!springs[i].bouncing){
					Shape springMask = new Rectangle(springs[i].pos.add(0, 12), new Vector(28, 21), Color.WHITE);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, springMask)){
						if(springs[i].type <= 3) p.vel = p.vel.project(new Vector(sin(springs[i].angle), cos(springs[i].angle)));
						else p.vel.x = p.vel.y = 0;
						p.vel.translate(new Vector(cos(springs[i].angle), -sin(springs[i].angle)).scale(springs[i].strength * SCALE));
						springs[i].bouncing = p.trickReadyReady = !(p.jumpReady = p.ground = p.jumpingUp = p.stopCam = false);
						p.bounceType = 0;
						p.state = STATE_BOUNCING;
						p.ps.playSound(SOUND_SPRING);
					}
				}
			}
		}
	}
	
	public static void ramps(Player p, Ramp[] ramps){
		if(ramps != null){
			for(int i = 0; i < ramps.length; i++){
				if(p.groundSpeed >= 10 * ramps[i].direction){
					Shape rampMask = new Rectangle(ramps[i].pos.add(5 * 8 * 2, -8 * 2), new Vector(8 * 2, 8 * 2), Color.WHITE);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, rampMask) && !p.ground){
						p.vel = new Vector(cos(ramps[i].angle) * ramps[i].strength * SCALE, -sin(ramps[i].angle) * ramps[i].strength * SCALE);
						p.jumpReady = p.ground = p.jumpingUp = p.stopCam = !(p.trickReadyReady = true);
						p.state = STATE_RAMP_DASHING;
						p.ps.playSound(SOUND_BOOST);
					}
				}
			}
		}
	}
	
	public static void rotors(Player p, Rotor[] rotors){
		if(p.state != STATE_SWINGING){
			if(rotors != null){
				boolean didSwing = false;
				for(int i = 0; i < rotors.length; i++){
					Shape rampMask = new Circle(rotors[i].pos.add(-8 * 2, 0), 8 * 2, Color.WHITE);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, rampMask) && !p.ground){
						didSwing = true;
						if(!p.justSwang){
							if(p.pos.y <= rotors[i].pos.y){
								p.swingStartFrame = 0;
								if(p.vel.x <= 0) p.swingDirection = p.facing;
								else p.swingDirection = -p.facing;
							}
							else{
								p.swingStartFrame = 6;
								if(p.vel.x >= 0) p.swingDirection = p.facing;
								else p.swingDirection = -p.facing;
							}
							
							p.vel = new Vector();
							p.groundSpeed = 0;
							p.pos = new Vector(rotors[i].pos.x, rotors[i].pos.y);
							p.stopCam = !(p.jumpReady = p.ground = p.jumpingUp = p.trickReady = p.trickReadyReady = false);
							p.state = STATE_SWINGING;
							p.rotor = rotors[i];
							p.rotor.facing = -p.facing;
							p.ps.playSound(SOUND_BOOST);
						}
					}
				}
				if(!didSwing) p.justSwang = false;
			}
		}
		else{
			if(p.spaceBar && p.jumpReady){
				double swingAngle = 0;
				if(p.swingAnim.frame == 9) swingAngle = 0;
				if(p.swingAnim.frame == 10) swingAngle = PI / 6;
				if(p.swingAnim.frame == 11) swingAngle = PI / 3;
				if(p.swingAnim.frame == 0) swingAngle = PI / 2;
				if(p.swingAnim.frame == 1) swingAngle = PI / 2 + PI / 6;
				if(p.swingAnim.frame == 2) swingAngle = PI / 2 + PI / 3;
				if(p.swingAnim.frame == 3) swingAngle = PI;
				if(p.swingAnim.frame == 4) swingAngle = PI + PI / 6;
				if(p.swingAnim.frame == 5) swingAngle = PI + PI / 3;
				if(p.swingAnim.frame == 6) swingAngle = PI + PI / 2;
				if(p.swingAnim.frame == 7) swingAngle = PI + PI / 2 + PI / 6;
				if(p.swingAnim.frame == 8) swingAngle = PI + PI / 2 + PI / 3;
				double posDistance = MASK_RADIUS * SCALE * 2;
				p.pos.translate(cos(swingAngle) * posDistance * p.facing, -sin(swingAngle) * posDistance);
				double swingPower = 20 * p.swingDirection;
				p.vel = new Vector(-sin(swingAngle) * swingPower * p.facing, -cos(swingAngle) * swingPower);
				p.rotor.anim.reset();
				p.rotor.facing = 1;
				p.stopCam = p.jumpReady = !(p.justSwang = true);
				p.state = STATE_JUMPING;
			}
			if(!p.spaceBar) p.jumpReady = true;
		}
	}
	
	public static void springPoles(Player p, SpringPole[] springPoles){
		if(p.state == STATE_SPRING_POLING){
			if(!p.springPole.bouncing){
				p.state = STATE_BOUNCING;
				p.bounceType = 1;
				double lowPower = -15d, highPower = -50d, springPower = 0d, xDif = 0d, wSpring = 5.5d * 8d * 2d;
				if(p.springPole.direction == 1){
					double xSpring = p.springPole.pos.x + 0.5 * 8 * 2;
					xDif = 1 - (p.pos.x - xSpring) / wSpring;
				}
				if(p.springPole.direction == -1){
					double xSpring = p.springPole.pos.x;
					xDif = 1 - (xSpring - p.pos.x) / wSpring;
				}
				xDif = min(max(xDif, 0), 1);
				springPower = lowPower + xDif * (highPower - lowPower);
				p.vel = new Vector(p.preSpringPoleXSpeed, springPower * SCALE);
				p.groundSpeed = p.preSpringPoleXSpeed;
			}
		}
		else{
			if(springPoles != null){
				for(int i = 0; i < springPoles.length; i++){
					p.springPole = springPoles[i];
					Shape springPoleMask = null;
					if(p.springPole.direction == 1) springPoleMask = new Rectangle(springPoles[i].pos.add(0.5d * 8d * 2d, 2d * 8d * 2d), new Vector(5.5d * 8d * 2d, 8d * 2d), Color.WHITE);
					if(p.springPole.direction == -1) springPoleMask = new Rectangle(springPoles[i].pos.add(0d, 2d * 8d * 2d), new Vector(5.5d * 8d * 2d, 8d * 2d), Color.WHITE);
					p.mask.relocate(p.pos);
					if(checkCollision(p.mask, springPoleMask) && !p.ground && p.vel.y >= 0){
						double xDif = 0d, wSpring = 5.5d * 8d * 2d;
						if(p.springPole.direction == 1){
							double xSpring = p.springPole.pos.x + 0.5 * 8 * 2;
							xDif = 1 - (p.pos.x - xSpring) / wSpring;
						}
						if(p.springPole.direction == -1){
							double xSpring = p.springPole.pos.x;
							xDif = 1 - (xSpring - p.pos.x) / wSpring;
						}
						xDif = min(max(xDif, 0), 1);
						p.preSpringPoleXSpeed = p.vel.x;
						p.vel = new Vector();
						p.groundSpeed = 0;
						if(xDif >= 0.5) p.springPole.fastBounce();
						else p.springPole.slowBounce();
						p.stopCam = p.trickReady = p.trickReadyReady = false;
						p.state = STATE_SPRING_POLING;
						if(p.anim != SPIN_ANIM){ p.anim = JUMP_ANIM; }
						p.ps.playSound(SOUND_SPRING_POLE);
						break;
					}
				}
			}
		}
	}
	
	public static void helixes(Player p, Helix[] helixes){
		if(helixes != null){
			for(int i = 0; i < helixes.length; i++){
				Shape helixMaskLeft = new Rectangle(helixes[i].pos.add(0, 96 * 2 - 16 * 2), new Vector(16 * 2, 16 * 2), Color.WHITE);
				Shape helixMaskRight = new Rectangle(helixes[i].pos.add(6 * 96 * 2 - 16 * 2, 96 * 2 - 16 * 2), new Vector(16 * 2, 16 * 2), Color.WHITE);
				Shape helixMaskEndLeft = new Rectangle(helixes[i].pos.add(-16 * 2, 96 * 2 - 16 * 2), new Vector(16 * 2, 16 * 2), Color.WHITE);
				Shape helixMaskEndRight = new Rectangle(helixes[i].pos.add(6 * 96 * 2, 96 * 2 - 16 * 2), new Vector(16 * 2, 16 * 2), Color.WHITE);
				p.mask.relocate(p.pos);
				if(p.helixing){
					if(p.helixDir == 1 && p.groundSpeed < 0 || p.helixDir == -1 && p.groundSpeed > 0){
						p.helixing = p.ground = false;
					}
					else{
						if(!checkCollision(p.mask, helixMaskLeft) && checkCollision(p.mask, helixMaskEndLeft) && p.helixDir == -1) p.helixing = false;
						if(!checkCollision(p.mask, helixMaskRight) && checkCollision(p.mask, helixMaskEndRight) && p.helixDir == 1) p.helixing = false;
					}
				}
				else{
					if(checkCollision(p.mask, helixMaskLeft) && p.ground && p.groundSpeed > 0 && !p.stopCam){
						p.helix = helixes[i];
						p.helixDir = 1;
						p.vel.y = 0;
						p.helixing = true;
					}
					if(checkCollision(p.mask, helixMaskRight) && p.ground && p.groundSpeed < 0 && !p.stopCam){
						p.helix = helixes[i];
						p.helixDir = -1;
						p.vel.y = 0;
						p.helixing = true;
					}
				}
			}
		}
		if(p.helixing){
			p.ground = true;
			double xDif = p.pos.x - p.helix.pos.x;
			if(xDif > 0) p.pos.y = p.helix.pos.y + 5 * MASK_RADIUS * SCALE + (3 * 8 * 2) * cos(xDif / (1.5 * 96 * 2) * PI) - 3 * 2;
			else p.pos.y = p.helix.pos.y + 96 * 2 - MASK_RADIUS * SCALE;
		}
	}
	
	public static void dashPads(Player p, DashPad[] dashPads){
		if(dashPads != null){
			for(int i = 0; i < dashPads.length; i++){
				Shape padMask = new Rectangle(dashPads[i].pos.add(0, 8 * 2), new Vector(2 * 8 * 2, 8 * 2), Color.WHITE);
				p.mask.relocate(p.pos);
				if(checkCollision(p.mask, padMask) && p.ground){
					p.vel = new Vector(GROUND_ACCEL_LIMIT * BOOST_LIMIT_SCALE * SCALE * dashPads[i].direction, 0);
					p.facing = dashPads[i].direction;
					p.boostMode = !(p.stopCam = false);
					if(p.state != STATE_SPINNING) p.state = STATE_DEFAULT;
					p.ps.playSound(SOUND_BOOST);
				}
			}
		}
	}
	
	public static void rails(Player p, Rail[] rails){ if(rails != null) for(int i = 0; i < rails.length; i++); }
	
	public static void blueSprings(Player p, BlueSpring[] blueSprings){
		if(blueSprings != null) for(int i = 0; i < blueSprings.length; i++) if(blueSprings[i].bouncing == 0){
			Shape springMask = new Rectangle(blueSprings[i].pos.add(0, 12), new Vector(28, 21), Color.WHITE);
			p.mask.relocate(p.pos);
			if(checkCollision(p.mask, springMask)){
				p.vel = p.vel.project(new Vector(1, 0));
				p.vel.translate(new Vector(0, -1).scale(55 * SCALE));
				blueSprings[i].slowBounce();
				p.jumpReady = p.ground = p.jumpingUp = p.stopCam = !(p.trickReadyReady = true);
				p.bounceType = 0;
				p.state = STATE_BOUNCING;
				p.ps.playSound(SOUND_SPRING);
			}
		}
	}
	
}
