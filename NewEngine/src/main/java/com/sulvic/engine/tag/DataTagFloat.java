package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sulvic.util.SulvicMath;

public class DataTagFloat implements IDataPrimitive{
	
	private float tagValue;
	
	DataTagFloat(){}
	
	public DataTagFloat(float value){ tagValue = value; }
	
	public static DataTagFloat getValue(float value){ return new DataTagFloat(value); }
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagFloat && tagValue == ((DataTagFloat)obj).tagValue;
	}
	
	public byte getId(){ return 0x5; }
	
	public byte getAsByte(){ return (byte)(SulvicMath.floorFloatInt(tagValue) & 0xFF); }
	
	public DataTagFloat copy(){ return this; }
	
	public double getAsDouble(){ return tagValue; }
	
	public float getAsFloat(){ return tagValue; }
	
	public int getAsInt(){ return SulvicMath.floorFloatInt(tagValue); }
	
	public int hashCode(){ return Float.floatToIntBits(tagValue); }
	
	public long getAsLong(){ return SulvicMath.floorFloatLong(tagValue); }
	
	public Number getAsNumber(){ return Float.valueOf(tagValue); }
	
	public short getAsShort(){ return (short)(SulvicMath.floorFloatInt(tagValue) & 0xFFFF); }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagValue = input.readFloat(); }
	
	public void write(DataOutput output) throws IOException{ output.writeFloat(tagValue); }
	
}
