package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataTagLong implements IDataPrimitive{
	
	private static final DataTagLong[] STORED_VALUES = new DataTagLong[1153];
	private long tagValue;
	
	DataTagLong(){}
	
	public DataTagLong(long value){ tagValue = value; }
	
	static{
		for(int i = 0; i < STORED_VALUES.length; i++) STORED_VALUES[i] = new DataTagLong((long)(i - 128));
	}
	
	public static DataTagLong getValue(long value){ return value >= -128L && value <= 1024L? STORED_VALUES[(int)value + 128]: new DataTagLong(value); }
	
	public byte getId(){ return 0x4; }
	
	public byte getAsByte(){ return (byte)(tagValue & 0xFFL); }
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagLong && tagValue == ((DataTagLong)obj).tagValue;
	}
	
	public DataTagLong copy(){ return this; }
	
	public double getAsDouble(){ return tagValue; }
	
	public float getAsFloat(){ return (float)tagValue; }
	
	public int getAsInt(){ return (int)(tagValue & 0xFFFFFFFFL); }
	
	public int hashCode(){ return (int)(tagValue ^ tagValue >>> 32L); }
	
	public long getAsLong(){ return tagValue; }
	
	public Number getAsNumber(){ return Long.valueOf(tagValue); }
	
	public short getAsShort(){ return (short)(tagValue & 0xFFFFL); }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagValue = input.readLong(); }
	
	public void write(DataOutput output) throws IOException{ output.writeLong(tagValue); }
	
}
