package objects;

import static java.lang.Math.*;

import static functionholders.CollisionFunctions.*;
import static functionholders.GeometryFunctions.*;
import static functionholders.ListFunctions.*;
import static functionholders.MathFunctions.*;
import static java.awt.event.KeyEvent.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedInputStream;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import datatypes.Animation;
import datatypes.Shape;
import datatypes.Vector;
import main.Loader;
import shapes.Circle;
import shapes.Rectangle;

public class Player {
	private final double SPRINT_ACCEL 		= 2;
	private final double MOVE_ACCEL  		= 0.3;
	private final double GROUND_ACCEL_LIMIT = 100;
	private final double SKID_ACCEL  		= 1;
	private final double DRAG_DECEL   		= 0.25;
	private final double JUMP_IMPULSE 		= 25;
	private final double JUMP_SWITCH  		= 2;
	private final double GRAVITY      		= 0.75;
	private final double SPIN_DECEL			= 0.025;
	
	private final double SLOW_MIN_SPEED 	= 20;
	private final double NORMAL_MIN_SPEED 	= 40;
	private final double FAST_MIN_SPEED 	= 60;
	private final double FASTEST_MIN_SPEED 	= 80;
	private final double SKID_MIN_SPEED 	= 5;
	
	private final double SPINDASH_MIN_STRENGTH = 50;
	private final double SPINDASH_CHARGE_SCALE = 10;
	private final double SPINDASH_MAX_STRENGTH = 100;
	
	private final double GROUND_ANGLE_MASK_OFFSET_X  = 0;
	private final double GROUND_ANGLE_MASK_OFFSET_Y  = 1;
	private final double GROUND_ANGLE_MASK_RADIUS    = 50;
	
	private final double STICK_OFFSET_SCALE = 0.5;
	private final double STICK_MIN_SPEED    = 10;
	
	private final double GROUND_MASK_OFFSET_X  = 0;
	private final double GROUND_MASK_OFFSET_Y  = 1;
	private final double GROUND_MASK_WIDTH     = 100;
	private final double GROUND_MASK_HEIGHT    = 50;
	
	private final double LEDGE_MASK_L_OFFSET_X  = -25;
	private final double LEDGE_MASK_L_OFFSET_Y  = 50;
	private final double LEDGE_MASK_L_WIDTH     = 50;
	private final double LEDGE_MASK_L_HEIGHT    = 50;
	
	private final double LEDGE_MASK_R_OFFSET_X  = 25;
	private final double LEDGE_MASK_R_OFFSET_Y  = 50;
	private final double LEDGE_MASK_R_WIDTH     = 50;
	private final double LEDGE_MASK_R_HEIGHT    = 50;
	
	private final double MASK_RADIUS = 50;
	
	private final double SCALE 					= 0.5;
	private final int    SPRITE_SCALE 			= 4;
	private final double ANIM_SPEED_SCALE 		= 0.4;
	private final double STEP_SOUND_SPEED		= 100;
	private final double STEP_SPEED_SCALE 		= 1;
	private final double STEP_SPEED_OFFSET		= 2;
	private final double MAX_STEP_SPEED 		= 25;
	private final double MIN_POTENTIAL_GRAVITY 	= 0.25;
	
	public boolean DRAW_MASKS 	= false;
	private final boolean DRAW_SPRITES	= true;
	
	private final int IDLE_ANIM 			= 0;
	private final int RUN_ANIM 				= 1;
	private final int FALL_ANIM 			= 2;
	private final int JUMP_ANIM 			= 3;
	private final int SKID_ANIM 			= 4;
	private final int SPIN_ANIM 			= 5;
	private final int CROUCH_ANIM_0 		= 6;
	private final int CROUCH_ANIM_1 		= 7;
	private final int SPINDASH_ANIM 		= 8;
	private final int SPINDASH_CHARGE_ANIM 	= 9;
	private final int SKIRT_ANIM		 	= 10;
	private final int TURN_ANIM			 	= 11;
	
	private final int NO_DUST_ANIM 		= 0;
	private final int REGULAR_DUST_ANIM = 1;
	private final int CHARGE_DUST_ANIM 	= 2;
	
	private boolean upArrow;
	private boolean downArrow;
	private boolean leftArrow;
	private boolean rightArrow;
	private boolean spaceBar;
	private boolean shiftKey;
	private boolean skidding;
	private boolean skirting;
	private boolean turning;
	
	private boolean ground;
	private boolean ledge;
	private boolean jumping;
	private boolean jumpReady;
	private boolean jumpSlowing;
	private boolean spinning;
	private boolean crouching0;
	private boolean crouching1;
	private boolean spindashReady;
	private boolean spindashing;
	private boolean spindashCharge;
	private boolean chargeReady;
	
	private double jumpSlowed;
	private double groundSpeed;
	private double spindashStrength;
	
	private double stepTimer;
	private int stepIndex;
	private int chargeDustTimer;
	public int layer;
	
	private Shape mask;
	
	public  Vector pos;
	private Vector vel;
	private Vector groundAxis;
	
	private Animation idleAnim;
	private Animation runSlowestAnim;
	private Animation runSlowAnim;
	private Animation runNormalAnim;
	private Animation runFastAnim;
	private Animation runFastestAnim;
	private Animation fallAnim;
	private Animation jumpAnim;
	private Animation skidAnim;
	private Animation spinAnim;
	private Animation crouchAnim0;
	private Animation crouchAnim1;
	private Animation spindashAnim;
	private Animation spindashChargeAnim;
	private Animation spindashDustAnim;
	private Animation spindashChargeDustAnim;
	private Animation skirtAnim;
	private Animation turnAnim;
	
	private int facing;
	private int anim;
	private int dustAnim;
	
	private Clip jumpSound0;
	private Clip jumpSound1;
	private Clip landSound;
	private Clip skidSound;
	private Clip spinSound;
	private Clip spindashChargeSound;
	private Clip spindashReleaseSound;
	private Clip stepSound0;
	private Clip stepSound1;
	private Clip stepSound2;
	private Clip stepSound3;
	private Clip stepSound4;
	
	public Player(double x, double y) {
		pos = new Vector(x, y);
		vel = new Vector();
		groundAxis = new Vector(0, 1);
		mask = new Circle(MASK_RADIUS);
		facing = 1;
		layer = 1;
		
		this.idleAnim = Loader.idleAnim;
		this.runSlowestAnim = Loader.runSlowestAnim;
		this.runSlowAnim    = Loader.runSlowAnim;
		this.runNormalAnim  = Loader.runNormalAnim;
		this.runFastAnim    = Loader.runFastAnim;
		this.runFastestAnim = Loader.runFastestAnim;
		this.fallAnim = Loader.fallAnim;
		this.jumpAnim = Loader.jumpAnim;
		this.skidAnim = Loader.skidAnim;
		this.spinAnim = Loader.spinAnim;
		this.crouchAnim0 = Loader.crouchAnim0;
		this.crouchAnim1 = Loader.crouchAnim1;
		this.spindashAnim = Loader.spindashAnim;
		this.spindashChargeAnim = Loader.spindashChargeAnim;
		this.spindashDustAnim = Loader.spindashDustAnim;
		this.spindashChargeDustAnim = Loader.spindashChargeDustAnim;
		this.skirtAnim = Loader.skirtAnim;
		this.turnAnim = Loader.turnAnim;
		
		this.jumpSound0 = Loader.jumpSound0;
		this.jumpSound1 = Loader.jumpSound1;
		this.landSound = Loader.landSound;
		this.skidSound = Loader.skidSound;
		this.spinSound = Loader.spinSound;
		this.spindashChargeSound = Loader.spindashChargeSound;
		this.spindashReleaseSound = Loader.spindashReleaseSound;
		this.stepSound0 = Loader.stepSound0;
		this.stepSound1 = Loader.stepSound1;
		this.stepSound2 = Loader.stepSound2;
		this.stepSound3 = Loader.stepSound3;
		this.stepSound4 = Loader.stepSound4;
	}
	
	public void update(Shape[] layer0, Shape[] layer1, Shape[] layer2, Shape[] layer1Triggers, Shape[] layer2Triggers, Shape[] platforms) {
		groundSpeed = getRotatedVectorComponents(vel, groundAxis).x;
		vel.translate(groundAxis.getPerpendicular().normalize().scale(groundSpeed));
		
		movement();
		drag();
		jump();
		spindash();
		crouch();
		gravity();
		
		boolean[] platMasks = checkPlatforms(platforms);
		
		// TODO: step velocity 1 unit at a time
		vel.translate(groundAxis.getPerpendicular().normalize().scale(-groundSpeed));
		pos.translate(vel);
		
		checkLayer(layer1Triggers, layer2Triggers);
		
		Shape[] shapes = null;
		if(layer == 1) {shapes = combine(layer0, layer1);}
		if(layer == 2) {shapes = combine(layer0, layer2);}
		shapes = combine(shapes, applyMask(platforms, platMasks));
		
		if(shapes != null) {
			// TODO: fix velocity projection for curves
			collide(shapes);
			checkLedge(shapes);
			stick(shapes);
		
			checkGround(shapes);
			getGroundAxis(shapes);
		}
	}
	
	private void movement() {
		double moveSpeed;
		if(!shiftKey) {moveSpeed = MOVE_ACCEL * SCALE;}
		else          {moveSpeed = SPRINT_ACCEL * SCALE;}
		
		if(!ground) {
			skidding = false;
			skirting = false;
			turning = false;
		}
		
		if(!crouching0) {
			if(!spinning) { // regular movement
				if(leftArrow && !rightArrow) {
					if(groundSpeed <= 0 || !ground) {
						if(!ground) {facing = -1;}
						if(skidding) {
							skidding = false;
							if(facing == 1) {skirting = true;}
						}
						groundSpeed -= moveSpeed;
						if(groundSpeed < -GROUND_ACCEL_LIMIT * SCALE) {groundSpeed = -GROUND_ACCEL_LIMIT * SCALE;}
					}
					else {
						groundSpeed -= SKID_ACCEL * SCALE;
						if(groundSpeed >= SKID_MIN_SPEED) {skidding = true;}
					}
				}
				if(rightArrow && !leftArrow) {
					if(groundSpeed >= 0 || !ground) {
						if(!ground) {facing = 1;}
						if(skidding) {
							skidding = false;
							if(facing == -1) {skirting = true;}
						}
						groundSpeed += moveSpeed;
						if(groundSpeed > GROUND_ACCEL_LIMIT * SCALE) {groundSpeed = GROUND_ACCEL_LIMIT * SCALE;}
					}
					else {
						groundSpeed += SKID_ACCEL * SCALE;
						if(groundSpeed <= -SKID_MIN_SPEED) {skidding = true;}
					}
				}
			}
			else { // movement while spinning
				if(ground) {
					if(leftArrow && !rightArrow) {
						if(groundSpeed > 0) {
							groundSpeed -= MOVE_ACCEL * SCALE;
							if(groundSpeed <= 0) {
								spinning = false;
								facing = -1;
							}
						}
						else {facing = -1;}
					}
					if(rightArrow && !leftArrow) {
						if(groundSpeed < 0) {
							groundSpeed += MOVE_ACCEL * SCALE;
							if(groundSpeed >= 0) {
								spinning = false;
								facing = 1;
							}
						}
						else {facing = 1;}
					}
				}
				else {
					if(leftArrow && !rightArrow) {
						groundSpeed -= MOVE_ACCEL * SCALE;
						if(groundSpeed < 0) {facing = -1;}
					}
					if(rightArrow && !leftArrow) {
						groundSpeed += MOVE_ACCEL * SCALE;
						if(groundSpeed > 0) {facing = 1;}
					}
				}
			}
		}
	}
	
	private void drag() {
		if(!leftArrow && !rightArrow && ground || !leftArrow && !rightArrow && ground) {
			skidding = false;
			
			if(!spinning) { // regular drag
				     if(groundSpeed > 0) {groundSpeed -= DRAG_DECEL * SCALE;}
				else if(groundSpeed < 0) {groundSpeed += DRAG_DECEL * SCALE;}
				
				if(groundSpeed >= -DRAG_DECEL * SCALE && groundSpeed <= DRAG_DECEL * SCALE) {groundSpeed = 0;}
			}
			else { // spinning drag
			         if(groundSpeed > 0) {groundSpeed -= SPIN_DECEL * SCALE;}
				else if(groundSpeed < 0) {groundSpeed += SPIN_DECEL * SCALE;}
				
				if(groundSpeed >= -SPIN_DECEL * SCALE && groundSpeed <= SPIN_DECEL * SCALE) {
					groundSpeed = 0;
					spinning = false;
				}
			}
			
			if(downArrow && groundSpeed != 0) {
				if(!spinning) {
					spinning = true;
					crouching0 = false;
					crouching1 = false;
					spindashReady = false;
					
					spinSound.stop();
					spinSound.flush();
					spinSound.setFramePosition(0);
					spinSound.start();
				}
			}
		}
	}

	private void jump() {
		if(ground && !spaceBar) {jumpReady = true;}
		if(!ground) {jumpReady = false;}
		else {jumping = false;}
		
		if(jumpReady && spaceBar && !crouching0) {
			jumpReady = false;
			ground = false;
			jumping = true;
			jumpSlowed = groundAxis.y * -JUMP_IMPULSE * SCALE;
			jumpSlowing = false;
			spinning = false;
			
			vel.translate(groundAxis.scale(-JUMP_IMPULSE * SCALE));
		}
		else {
			if(jumping) {
				if(!spaceBar || jumpSlowing) {
					vel.translate(0, JUMP_SWITCH * SCALE);
					jumpSlowed += JUMP_SWITCH * SCALE;
					jumpSlowing = true;
				}
				
				if(jumpSlowed >= 0) {jumping = false;}
			}
		}
	}
	
	private void spindash() {
		if(spindashReady && spaceBar) {
			if(!spindashing) {
				spindashing = true;
				spindashCharge = false;
				chargeReady = false;
				chargeDustTimer = 45;
				spindashStrength = SPINDASH_MIN_STRENGTH * SCALE;
				
				spindashChargeSound.stop();
				spindashChargeSound.flush();
				spindashChargeSound.setFramePosition(0);
				spindashChargeSound.start();
			}
		}
		
		if(spindashing) {
			if(downArrow) {
				if(spaceBar && chargeReady) {
					chargeDustTimer = 45;
					spindashCharge = true;
					spindashStrength += SPINDASH_CHARGE_SCALE * SCALE;
					spindashStrength = min(spindashStrength, SPINDASH_MAX_STRENGTH);
					
					spindashChargeSound.stop();
					spindashChargeSound.flush();
					spindashChargeSound.setFramePosition(0);
					spindashChargeSound.start();
				}
				
				if(!spaceBar) {chargeReady = true;}
				else {chargeReady = false;}
			}
			else {
				crouching0 = false;
				crouching1 = false;
				spindashReady = false;
				spindashing = false;
				spinning = true;
				groundSpeed += spindashStrength * facing;
				
				spindashReleaseSound.stop();
				spindashReleaseSound.flush();
				spindashReleaseSound.setFramePosition(0);
				spindashReleaseSound.start();
			}
		}
	}
	
	private void crouch() {
		if(ground && groundSpeed == 0 && downArrow) {
			crouching0 = true;
			crouching1 = true;
			spindashReady = false;
		}
		if(crouching0 && !downArrow) {
			crouching1 = false;
			spindashReady = false;
		}
		
		if(crouching0 && groundSpeed != 0) {
			crouching0 = false;
			crouching1 = false;
			spindashReady = false;
			spinning = true;
			
			spinSound.stop();
			spinSound.flush();
			spinSound.setFramePosition(0);
			spinSound.start();
		}
	}

	private void gravity() {
		if(!ground) {
			vel.translate(0, GRAVITY * SCALE);
			
			if(jumping) {jumpSlowed += GRAVITY * SCALE;}
		}
		else {
			Vector tempGrav = new Vector(0, GRAVITY * SCALE).project(groundAxis.getPerpendicular().normalize());
			if(tempGrav.getLength() >= MIN_POTENTIAL_GRAVITY) {groundSpeed += getRotatedVectorComponents(tempGrav, groundAxis).x;}
		}
	}

	private void checkLayer(Shape[] layer1Triggers, Shape[] layer2Triggers) {
		mask = new Circle(MASK_RADIUS * SCALE);
		mask.relocate(pos);
		
		if(layer2Triggers != null) {for(int i = 0; i < layer2Triggers.length; i++) {if(checkCollision(mask, layer2Triggers[i])) {layer = 2;}}}
		if(layer1Triggers != null) {for(int i = 0; i < layer1Triggers.length; i++) {if(checkCollision(mask, layer1Triggers[i])) {layer = 1;}}}
	}
	
	private void collide(Shape[] shapes) {
		mask = new Circle(MASK_RADIUS * SCALE);
		mask.relocate(pos);
		Vector dir = clip(mask, shapes);
		
		pos.translate(dir);
		
		if(dir.getLength() != 0) {
			dir = dir.getPerpendicular();
			vel = vel.project(dir);
		}
	}
	
	private boolean[] checkPlatforms(Shape[] shapes) {
		boolean[] out = new boolean[shapes.length];
		
		for(int i = 0; i < shapes.length; i++) {
			out[i] = true;
			for(int p = 0; p < shapes[i].points.length; p++) {if(pos.y + MASK_RADIUS * SCALE > shapes[i].points[p].y) {out[i] = false;}}
		}
		
		return(out);
	}

	private void checkLedge(Shape[] shapes) {
		Shape groundMask0 = getRotatedRectangle(pos, LEDGE_MASK_L_WIDTH * SCALE, LEDGE_MASK_L_HEIGHT, LEDGE_MASK_L_OFFSET_X * SCALE, LEDGE_MASK_L_OFFSET_Y * SCALE);
		Shape groundMask1 = getRotatedRectangle(pos, LEDGE_MASK_R_WIDTH * SCALE, LEDGE_MASK_R_HEIGHT, LEDGE_MASK_R_OFFSET_X * SCALE, LEDGE_MASK_R_OFFSET_Y * SCALE);
		
		boolean ground0 = false;
		boolean ground1 = false;
		for(int i = 0; i < shapes.length; i++) {if(checkCollision(groundMask0, shapes[i])) {ground0 = true;}}
		for(int i = 0; i < shapes.length; i++) {if(checkCollision(groundMask1, shapes[i])) {ground1 = true;}}
		
		ledge = !(ground0 && ground1);
		if(ledge) {groundAxis = new Vector(0, 1);}
	}
	
	private void stick(Shape[] shapes) {
		if(ground && !ledge) {
			pos.translate(groundAxis.scale(STICK_OFFSET_SCALE * SCALE * vel.getLength()));
			mask = new Circle(MASK_RADIUS * SCALE);
			mask.relocate(pos);
			
			Vector dir = clip(mask, shapes);
			
			pos.translate(dir);
			
			if(dir.getLength() != 0) {
				dir = dir.getPerpendicular();
				vel = vel.project(dir);
			}
		}
	}

	private void checkGround(Shape[] shapes) {
		boolean oldGround = ground;
		
		Shape groundMask = getRotatedCircle(pos, GROUND_ANGLE_MASK_RADIUS * SCALE, GROUND_ANGLE_MASK_OFFSET_X * SCALE, GROUND_ANGLE_MASK_OFFSET_Y * SCALE);
		
		ground = false;
		for(int i = 0; i < shapes.length; i++) {if(checkCollision(groundMask, shapes[i])) {ground = true;}}
		
		if(ground && !oldGround) {
			ledge = false;
			
			landSound.stop();
			landSound.flush();
			landSound.setFramePosition(0);
			landSound.start();
			
			if(downArrow) {
				if(!spinning && vel.x != 0) {
					spinning = true;
					
					spinSound.stop();
					spinSound.flush();
					spinSound.setFramePosition(0);
					spinSound.start();
				}
			}
			else {spinning = false;}
		}
		
	}
	
	private void getGroundAxis(Shape[] shapes) {
		if(abs(groundSpeed) < STICK_MIN_SPEED * SCALE) {groundAxis = new Vector(0, 1);}
		
		if(ground && !ledge) {
			Shape groundMask;
			
			groundMask = getRotatedCircle(pos, GROUND_ANGLE_MASK_RADIUS * SCALE, GROUND_ANGLE_MASK_OFFSET_X * SCALE, GROUND_ANGLE_MASK_OFFSET_Y * SCALE);
			
			groundAxis = new Vector(0, 0);
			groundAxis.translate(clip(groundMask, shapes));
			
			if(groundAxis.getLength() != 0) {groundAxis = groundAxis.scale(-1).normalize();}
			else {groundAxis = new Vector(0, 1);}
		}
		else {groundAxis = new Vector(0, 1);}
	}
	
	private void manageAnimations() {
		if(spindashing) {
			if(!spindashCharge) {
				if(anim == SPINDASH_CHARGE_ANIM) {
					spindashChargeAnim.update(1);
					if(spindashChargeAnim.finished) {
						anim = SPINDASH_ANIM;
						spindashAnim.reset();
					}
				}
				else if(anim == SPINDASH_ANIM) {spindashAnim.update(1);}
				else {
					anim = SPINDASH_ANIM;
					spindashAnim.reset();
				}
			}
			if(spindashCharge) {
				anim = SPINDASH_CHARGE_ANIM;
				spindashChargeAnim.reset();
				spindashCharge = false;
			}
			
			if(chargeDustTimer == 0) {
				if(dustAnim == REGULAR_DUST_ANIM) {spindashDustAnim.update(1);}
				else {
					dustAnim = REGULAR_DUST_ANIM;
					spindashDustAnim.reset();
				}
			}
			else {
				if(dustAnim == CHARGE_DUST_ANIM) {spindashChargeDustAnim.update(1);}
				else {
					dustAnim = CHARGE_DUST_ANIM;
					spindashChargeDustAnim.reset();
				}
				
				chargeDustTimer--;
			}
		}
		else {
			dustAnim = NO_DUST_ANIM;
			
			if(crouching1) {
				if(anim == CROUCH_ANIM_0) {
					crouchAnim0.update(1);
					if(crouchAnim0.finished) {spindashReady = true;}
				}
				else {
					anim = CROUCH_ANIM_0;
					crouchAnim0.reset();
				}
			}
			else if(crouching0) {
				if(anim == CROUCH_ANIM_1) {
					crouchAnim1.update(1);
					if(crouchAnim1.finished) {
						anim = IDLE_ANIM;
						idleAnim.reset();
						crouching0 = false;
					}
				}
				else {
					anim = CROUCH_ANIM_1;
					crouchAnim1.reset();
				}
			}
			else {
				if(spinning) {
					if(anim == SPIN_ANIM) {spinAnim.update(1);}
					else {
						anim = SPIN_ANIM;
						spinAnim.reset();
					}
				}
				else {
					if(ground) {
						if(groundSpeed == 0 && !turning && !skidding && !skirting) {
							if(anim == IDLE_ANIM) {idleAnim.update(1);}
							else {
								anim = IDLE_ANIM;
								idleAnim.reset();
							}
						}
						else {
							if(skidding || skirting) {
								if(skidding) {
									if(anim == SKID_ANIM) {skidAnim.update(1);}
									else {
										anim = SKID_ANIM;
										skidAnim.reset();
										
										skidSound.stop();
										skidSound.flush();
										skidSound.setFramePosition(0);
										skidSound.start();
									}
								}
								else {
									if(skirting) {
										if(anim == SKIRT_ANIM) {
											skirtAnim.update(1);
											if(skirtAnim.finished) {
												skirting = false;
												
												if(groundSpeed == 0) {
													anim = IDLE_ANIM;
													idleAnim.reset();
												}
												else {
													anim = RUN_ANIM;
													runSlowestAnim.reset();
													runSlowAnim.reset();
													runNormalAnim.reset();
													runFastAnim.reset();
													runFastestAnim.reset();
												}
												
												facing *= -1;
											}
										}
										else {
											anim = SKIRT_ANIM;
											skirtAnim.reset();
										}
									}
									else {
										if(groundSpeed == 0) {
											anim = IDLE_ANIM;
											idleAnim.reset();
										}
										else {
											anim = RUN_ANIM;
											runSlowestAnim.reset();
											runSlowAnim.reset();
											runNormalAnim.reset();
											runFastAnim.reset();
											runFastestAnim.reset();
										}
									}
								}
							}
							else {
								if(facing == 1 && groundSpeed < 0 && leftArrow || facing == -1 && groundSpeed > 0 && rightArrow || turning) {
									turning = true;
									
									if(anim == TURN_ANIM) {
										turnAnim.update(1);
										if(turnAnim.finished) {
											turning = false;
											
											if(groundSpeed == 0) {
												anim = IDLE_ANIM;
												idleAnim.reset();
											}
											else {
												anim = RUN_ANIM;
												runSlowestAnim.reset();
												runSlowAnim.reset();
												runNormalAnim.reset();
												runFastAnim.reset();
												runFastestAnim.reset();
											}
											
											facing *= -1;
										}
									}
									else {
										anim = TURN_ANIM;
										turnAnim.reset();
									}
								}
								else {
									if(anim == RUN_ANIM) {
										runSlowestAnim.update(abs(groundSpeed) * ANIM_SPEED_SCALE * SCALE + 0.25);
										runSlowAnim.   update(abs(groundSpeed) * ANIM_SPEED_SCALE * SCALE + 0.25);
										runNormalAnim. update(abs(groundSpeed) * ANIM_SPEED_SCALE * SCALE + 0.25);
										runFastAnim.   update(abs(groundSpeed) * ANIM_SPEED_SCALE * SCALE + 0.25);
										runFastestAnim.update(abs(groundSpeed) * ANIM_SPEED_SCALE * SCALE + 0.25);
										
										stepTimer += min(abs(groundSpeed) * SCALE * STEP_SPEED_SCALE + STEP_SPEED_OFFSET, MAX_STEP_SPEED);
										if(stepTimer >= STEP_SOUND_SPEED) {
											stepTimer = 0;
											
											switch(stepIndex) {
												case(0): {
													stepSound0.stop();
													stepSound0.flush();
													stepSound0.setFramePosition(0);
													stepSound0.start();
													break;
												}
												case(1): {
													stepSound1.stop();
													stepSound1.flush();
													stepSound1.setFramePosition(0);
													stepSound1.start();
													break;
												}
												case(2): {
													stepSound2.stop();
													stepSound2.flush();
													stepSound2.setFramePosition(0);
													stepSound2.start();
													break;
												}
												case(3): {
													stepSound3.stop();
													stepSound3.flush();
													stepSound3.setFramePosition(0);
													stepSound3.start();
													break;
												}
												case(4): {
													stepSound4.stop();
													stepSound4.flush();
													stepSound4.setFramePosition(0);
													stepSound4.start();
													break;
												}
											}
											
											stepIndex++;
											if(stepIndex == 5) {stepIndex = 0;}
										}
									}
									else {
										anim = RUN_ANIM;
										runSlowestAnim.reset();
										runSlowAnim.reset();
										runNormalAnim.reset();
										runFastAnim.reset();
										runFastestAnim.reset();
									}
								}
							}
						}
					}
					else {
						if(jumping) {
							if(anim != JUMP_ANIM) {
								anim = JUMP_ANIM;
								jumpAnim.reset();
								
								jumpSound0.stop();
								jumpSound1.stop();
								jumpSound0.flush();
								jumpSound1.flush();
								jumpSound0.setFramePosition(0);
								jumpSound1.setFramePosition(0);
								jumpSound0.start();
								jumpSound1.start();
							}
						}
						
						if(anim == FALL_ANIM) {fallAnim.update(1);}
						else {
							if(anim != JUMP_ANIM) {
								anim = FALL_ANIM;
								fallAnim.reset();
							}
						}
						
						if(anim == JUMP_ANIM) {jumpAnim.update(1);}
					}
				}
			}
		}
	}
	
	public void draw(Graphics2D graphics) {
		manageAnimations();
		
		if(DRAW_MASKS) {
			Shape temp;
			
			temp = getRotatedCircle(new Vector(Loader.graphicsWidth / 2, Loader.graphicsHeight / 2), GROUND_ANGLE_MASK_RADIUS * SCALE, GROUND_ANGLE_MASK_OFFSET_X * SCALE, GROUND_ANGLE_MASK_OFFSET_Y * SCALE);
			temp.color = Color.CYAN;
			temp.draw(graphics, new Vector());
			
			temp = getRotatedRectangle(new Vector(Loader.graphicsWidth / 2, Loader.graphicsHeight / 2), LEDGE_MASK_L_WIDTH * SCALE, LEDGE_MASK_L_HEIGHT * SCALE, LEDGE_MASK_L_OFFSET_X * SCALE, LEDGE_MASK_L_OFFSET_Y * SCALE);
			temp.color = Color.BLUE;
			temp.draw(graphics, new Vector());
			
			temp = getRotatedRectangle(new Vector(Loader.graphicsWidth / 2, Loader.graphicsHeight / 2), LEDGE_MASK_R_WIDTH * SCALE, LEDGE_MASK_R_HEIGHT * SCALE, LEDGE_MASK_R_OFFSET_X * SCALE, LEDGE_MASK_R_OFFSET_Y * SCALE);
			temp.color = Color.BLUE;
			temp.draw(graphics, new Vector());
			
			temp = getRotatedRectangle(new Vector(Loader.graphicsWidth / 2, Loader.graphicsHeight / 2), GROUND_MASK_WIDTH * SCALE, GROUND_MASK_HEIGHT * SCALE, GROUND_MASK_OFFSET_X * SCALE, GROUND_MASK_OFFSET_Y * SCALE);
			temp.color = Color.PINK;
			temp.draw(graphics, new Vector());
			
			temp = new Circle(MASK_RADIUS * SCALE);
			temp.relocate(new Vector(Loader.graphicsWidth / 2, Loader.graphicsHeight / 2));
			if(ground) {temp.color = Color.GREEN;}
			temp.draw(graphics, new Vector());
		}
		if(DRAW_SPRITES) {
			if(anim == RUN_ANIM) {
				     if(abs(groundSpeed) >= FASTEST_MIN_SPEED * SCALE) {runFastestAnim.draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE), (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
				else if(abs(groundSpeed) >= FAST_MIN_SPEED    * SCALE) {runFastAnim.   draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE), (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
				else if(abs(groundSpeed) >= NORMAL_MIN_SPEED  * SCALE) {runNormalAnim. draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE), (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
				else if(abs(groundSpeed) >= SLOW_MIN_SPEED    * SCALE) {runSlowAnim.   draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE), (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
				else                                                   {runSlowestAnim.draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE), (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			}
			if(anim == IDLE_ANIM)            {idleAnim.          draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == FALL_ANIM)            {fallAnim.          draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == SKID_ANIM)            {skidAnim.          draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == SKIRT_ANIM)           {skirtAnim.         draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == TURN_ANIM)            {turnAnim.          draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == CROUCH_ANIM_0)        {crouchAnim0.       draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing +  4 * SPRITE_SCALE * SCALE * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == CROUCH_ANIM_1)        {crouchAnim1.       draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing +  4 * SPRITE_SCALE * SCALE * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE),                             (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == SPINDASH_ANIM)        {spindashAnim.      draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE) -  3 * SPRITE_SCALE * SCALE, (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == SPINDASH_CHARGE_ANIM) {spindashChargeAnim.draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing,                                       Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE) -  3 * SPRITE_SCALE * SCALE, (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == JUMP_ANIM)            {jumpAnim.          draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing + 16 * SPRITE_SCALE * SCALE * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE) + 36 * SPRITE_SCALE * SCALE, (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(anim == SPIN_ANIM)            {spinAnim.          draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing + 16 * SPRITE_SCALE * SCALE * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE) + 36 * SPRITE_SCALE * SCALE, (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, -PI / 2, graphics);}
		
			if(dustAnim == REGULAR_DUST_ANIM) {spindashDustAnim.      draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE) -  3 * SPRITE_SCALE * SCALE, (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
			if(dustAnim == CHARGE_DUST_ANIM)  {spindashChargeDustAnim.draw(Loader.graphicsWidth / 2 - idleAnim.getCurrentSize()[0] * SPRITE_SCALE * SCALE / 2 * -facing, Loader.graphicsHeight / 2 - idleAnim.getCurrentSize()[1] * SPRITE_SCALE * SCALE / 2 - SPRITE_SCALE * SCALE / 2 - 14 * (SPRITE_SCALE * SCALE) -  3 * SPRITE_SCALE * SCALE, (int)(-facing * SPRITE_SCALE * SCALE), (int)(SPRITE_SCALE * SCALE), Loader.graphicsWidth / 2, Loader.graphicsHeight / 2, getAngleOfVector(groundAxis), graphics);}
		}
	}

	public void keyPressed(int key) {
		switch(key) {
			case(VK_UP): {upArrow = true; break;}
			case(VK_DOWN): {downArrow = true; break;}
			case(VK_LEFT): {leftArrow = true; break;}
			case(VK_RIGHT): {rightArrow = true; break;}
			case(VK_SPACE): {spaceBar = true; break;}
			case(VK_SHIFT): {shiftKey = true; break;}
		}
	}
	public void keyReleased(int key) {
		switch(key) {
			case(VK_UP): {upArrow = false; break;}
			case(VK_DOWN): {downArrow = false; break;}
			case(VK_LEFT): {leftArrow = false; break;}
			case(VK_RIGHT): {rightArrow = false; break;}
			case(VK_SPACE): {spaceBar = false; break;}
			case(VK_SHIFT): {shiftKey = false; break;}
		}
	}
	
	private Shape getRotatedCircle(Vector pos, double radius, double offsetX, double offsetY) {
		Shape groundMask = new Circle(radius);
		groundMask.relocate(pos);
		groundMask.translate(groundAxis.normalize().scale(offsetY - radius + MASK_RADIUS * SCALE));
		groundMask.translate(groundAxis.getPerpendicular().normalize().scale(-offsetX));
		
		return(groundMask);
	}
	
	private Shape getRotatedRectangle(Vector pos, double w, double h, double offsetX, double offsetY) {
		Shape groundMask = new Rectangle(w, h);
		groundMask.relocate(pos);
		
		groundMask = rotateRectangle(groundMask, groundAxis);
		groundMask.translate(groundAxis.normalize().scale(offsetY - h / 2 + MASK_RADIUS * SCALE));
		groundMask.translate(groundAxis.getPerpendicular().normalize().scale(-offsetX));
		
		return(groundMask);
	}
	
	private Vector getRotatedVectorComponents(Vector vel, Vector groundAxis) {
		Vector testVel = vel.getNew();
		if(!groundAxis.normalize().checkEqual(new Vector(0, 1))) {
			Vector tempGroundAxis = groundAxis.getPerpendicular().normalize();
			tempGroundAxis.x *= -1;
			if(testVel.getLength() != 0) {
				testVel.rotateAroundPointTo(new Vector(0, 0), tempGroundAxis);
			}
		}
		
		return(testVel);
	}
}
