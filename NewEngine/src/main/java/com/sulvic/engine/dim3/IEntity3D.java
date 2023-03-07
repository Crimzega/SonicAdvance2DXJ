package com.sulvic.engine.dim3;

import com.sulvic.engine.dim2.IEntity2D;

public interface IEntity3D extends IEntity2D{
	
	double getLastPosZ();
	
	double getPosZ();
	
	void setPosZ(double z);
	
}
