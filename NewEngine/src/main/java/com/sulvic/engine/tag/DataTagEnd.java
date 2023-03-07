package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataTagEnd implements IDataTag{
	
	DataTagEnd(){}
	
	public byte getId(){ return 0x0; }
	
	public IDataTag copy(){ return this; }
	
	public int hashCode(){ return 0; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{}
	
	public void write(DataOutput output) throws IOException{}

}
