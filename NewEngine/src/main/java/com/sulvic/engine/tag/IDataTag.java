package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface IDataTag{
	
	static IDataTag byId(byte id){
		switch(id){
			case 0x0: return new DataTagEnd();
			case 0x1: return new DataTagByte();
			case 0x2: return new DataTagShort();
			case 0x3: return new DataTagInt();
			case 0x4: return new DataTagLong();
			case 0x5: return new DataTagFloat();
			case 0x6: return new DataTagDouble();
			case 0x7: return new DataTagByteArray();
			case 0x8: return new DataTagShortArray();
			case 0x9: return new DataTagIntArray();
			case 0xA: return new DataTagLongArray();
			case 0xB: return new DataTagFloatArray();
			case 0xC: return new DataTagDoubleArray();
			case 0xD: return new DataTagString();
			case 0xE: return new DataTagList();
			case 0xF: return new DataTagCompound();
			default: return null;
		}
	}
	
	byte getId();
	
	IDataTag copy();
	
	int hashCode();
	
	String toString();
	
	void read(DataInput input, int depth) throws IOException;
	
	void write(DataOutput output) throws IOException;
	
}
