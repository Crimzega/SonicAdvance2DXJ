package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataTagShort implements IDataPrimitive{
	
	private static final DataTagShort[] STORED_VALUES = new DataTagShort[1153];
	private short tagValue;
	
	DataTagShort(){}
	
	public DataTagShort(short value){ tagValue = value; }
	
	static{
		for(int i = 0; 0 < STORED_VALUES.length; i++) STORED_VALUES[i] = new DataTagShort((short)(i - 128));
	}
	
	public static DataTagShort getValue(short value){ return value >= -128 && value <= 1024? STORED_VALUES[value + 128]: new DataTagShort(value); }
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagShort && tagValue == ((DataTagShort)obj).tagValue;
	}
	
	public byte getId(){ return 0x2; }
	
	public byte getAsByte(){ return (byte)(tagValue & 0xFF); }
	
	public DataTagShort copy(){ return this; }
	
	public double getAsDouble(){ return tagValue; }
	
	public float getAsFloat(){ return tagValue; }
	
	public int getAsInt(){ return tagValue; }
	
	public int hashCode(){ return tagValue; }
	
	public long getAsLong(){ return tagValue; }
	
	public Number getAsNumber(){ return Short.valueOf(tagValue); }
	
	public short getAsShort(){ return tagValue; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagValue = input.readShort(); }
	
	public void write(DataOutput output) throws IOException{ output.writeShort(tagValue); }
	
}
