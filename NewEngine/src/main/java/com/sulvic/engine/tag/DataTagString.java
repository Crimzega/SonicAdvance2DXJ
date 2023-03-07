package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sulvic.util.StringHelper;

public class DataTagString implements IDataTag{
	
	private String tagString;
	
	DataTagString(){}
	
	public DataTagString(String str){
		if(StringHelper.isNullOrEmpty(str)) throw new NullPointerException("An empty string is not allowed");
		tagString = str;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagString && tagString.equals(((DataTagString)obj).tagString);
	}
	
	public byte getId(){ return 0xD; }
	
	public IDataTag copy(){ return this; }
	
	public int hashCode(){ return tagString.hashCode(); }
	
	public String getString(){ return tagString; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{ tagString = input.readUTF(); }
	
	public void write(DataOutput output) throws IOException{ output.writeUTF(tagString); }
	
}
