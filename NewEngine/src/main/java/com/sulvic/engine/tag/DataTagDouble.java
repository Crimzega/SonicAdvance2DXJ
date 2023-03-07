package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sulvic.util.SulvicMath;

public class DataTagDouble implements IDataPrimitive{
	
	private double tagValue;
	
	DataTagDouble(){}
	
	public DataTagDouble(double value){ tagValue = value; }
	
	public static DataTagDouble getValue(double value){ return new DataTagDouble(value); }
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagDouble && tagValue == ((DataTagDouble)obj).tagValue;
	}
	
	public byte getAsByte(){ return (byte)(SulvicMath.floorDoubleInt(tagValue) & 0xFF); }
	
	public byte getId(){ return 0x6; }
	
	public DataTagDouble copy(){ return this; }
	
	public double getAsDouble(){ return tagValue; }
	
	public float getAsFloat(){ return (float)tagValue; }
	
	public int getAsInt(){ return SulvicMath.floorDoubleInt(tagValue); }
	
	public int hashCode(){
		long temp = Double.doubleToLongBits(tagValue);
		return (int)(temp ^ temp >>> 32L);
	}
	
	public long getAsLong(){ return SulvicMath.floorDoubleLong(tagValue); }
	
	public Number getAsNumber(){ return Double.valueOf(tagValue); }
	
	public short getAsShort(){ return (short)(SulvicMath.floorDoubleInt(tagValue) & 0xFFFF); }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagValue = input.readDouble(); }
	
	public void write(DataOutput output) throws IOException{ output.writeDouble(tagValue); }
	
}
