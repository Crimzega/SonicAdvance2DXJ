package com.sulvic.engine.tag;

public interface IDataPrimitive extends IDataTag{
	
	byte getAsByte();
	
	double getAsDouble();
	
	float getAsFloat();
	
	int getAsInt();
	
	long getAsLong();
	
	Number getAsNumber();
	
	short getAsShort();
	
}
