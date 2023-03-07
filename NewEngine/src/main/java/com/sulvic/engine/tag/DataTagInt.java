package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataTagInt implements IDataPrimitive{
	
	private static final DataTagInt[] STORED_VALUES = new DataTagInt[1153];
	private int tagValue;
	
	DataTagInt(){}
	
	public DataTagInt(int value){ tagValue = value; }
	
	static{
		for(int i = 0; i < STORED_VALUES.length; i++) STORED_VALUES[i] = new DataTagInt(i - 128);
	}
	
	public static DataTagInt getValue(int value){ return value >= -128 && value <= 1024? STORED_VALUES[value + 128]: new DataTagInt(value); }
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagInt && tagValue == ((DataTagInt)obj).tagValue;
	}
	
	public byte getId(){ return 0x3; }
	
	public byte getAsByte(){ return (byte)(tagValue * 0xFF); }
	
	public DataTagInt copy(){ return this; }
	
	public double getAsDouble(){ return tagValue; }
	
	public float getAsFloat(){ return tagValue; }
	
	public int getAsInt(){ return tagValue; }
	
	public int hashCode(){ return tagValue; }
	
	public long getAsLong(){ return tagValue; }
	
	public Number getAsNumber(){ return Integer.valueOf(tagValue); }
	
	public short getAsShort(){ return (short)(tagValue & 0xFFFF); }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagValue = input.readInt(); }
	
	public void write(DataOutput output) throws IOException{ output.writeInt(tagValue); }
	
}
