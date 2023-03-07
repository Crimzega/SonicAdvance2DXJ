package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataTagByte implements IDataPrimitive{
	
	private static final DataTagByte[] STORED_VALUES = new DataTagByte[256];
	public static final DataTagByte FALSE = getValue((byte)0), TRUE = getValue((byte)1);
	private byte tagValue;
	
	DataTagByte(){}
	
	public DataTagByte(byte value){ tagValue = value; }
	
	static{
		for(int i = 0; i < STORED_VALUES.length; i++) STORED_VALUES[i] = new DataTagByte((byte)(i - 128));
	}
	
	public static DataTagByte getValue(byte value){ return STORED_VALUES[value + 128]; }
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagByte && tagValue == ((DataTagByte)obj).tagValue;
	}
	
	public byte getId(){ return 0x1; }
	
	public byte getAsByte(){ return tagValue; }
	
	public DataTagByte copy(){ return new DataTagByte(tagValue); }
	
	public double getAsDouble(){ return tagValue; }
	
	public float getAsFloat(){ return tagValue; }
	
	public int getAsInt(){ return tagValue; }
	
	public int hashCode(){ return tagValue; }
	
	public long getAsLong(){ return tagValue; }
	
	public Number getAsNumber(){ return Byte.valueOf(tagValue); }
	
	public short getAsShort(){ return tagValue; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagValue = input.readByte(); }
	
	public void write(DataOutput output) throws IOException{ output.writeByte(tagValue); }
	
}
