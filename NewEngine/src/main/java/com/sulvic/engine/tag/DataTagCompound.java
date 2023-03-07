package com.sulvic.engine.tag;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.sulvic.engine.exception.ComplexTagException;
import com.sulvic.util.ContentBuilder;

public class DataTagCompound implements IDataTag{
	
	private Map<String, IDataTag> tagMap;
	
	public DataTagCompound(){ this(ContentBuilder.newHashMap()); }
	
	public DataTagCompound(Map<String, IDataTag> map){ tagMap = map; }
	
	public byte[] getByteArray(String name){
		try{
			if(containsKey(name, (byte)0x7)) return ((DataTagByteArray)getTag(name)).getValues();
		}
		catch(ClassCastException ex){}
		return new byte[0];
	}
	
	public double[] getDoubleArray(String name){
		try{
			if(containsKey(name, (byte)0xC)) return ((DataTagDoubleArray)getTag(name)).getValues();
		}
		catch(ClassCastException ex){}
		return new double[0];
	}
	
	public float[] getFloatArray(String name){
		try{
			if(containsKey(name, (byte)0xB)) return ((DataTagFloatArray)getTag(name)).getValues();
		}
		catch(ClassCastException ex){}
		return new float[0];
	}
	
	public int[] getIntArray(String name){
		try{
			if(containsKey(name, (byte)0x9)) return ((DataTagIntArray)getTag(name)).getValues();
		}
		catch(ClassCastException ex){}
		return new int[0];
	}
	
	public long[] getLongArray(String name){
		try{
			if(containsKey(name, (byte)0xA)) return ((DataTagLongArray)getTag(name)).getValues();
		}
		catch(ClassCastException ex){}
		return new long[0];
	}
	
	public short[] getShortArray(String name){
		try{
			if(containsKey(name, (byte)0x8)) return ((DataTagShortArray)getTag(name)).getValues();
		}
		catch(ClassCastException ex){}
		return new short[0];
	}
	
	public boolean getBoolean(String name){ return getByte(name) == 0x1; }
	
	public boolean containsKey(String name){ return getMap().containsKey(name); }
	
	public boolean containsKey(String name, byte specifId){
		byte id = getTagId(name);
		return id == specifId || (specifId == 0x63 && (id == 0x1 || id == 0x2 || id == 0x3 || id == 0x4 || id == 0x5 || id == 0x6));
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagCompound && getMap().equals(((DataTagCompound)obj).getMap());
	}
	
	public byte getByte(String name){
		try{
			if(containsKey(name, (byte)0x1)) return ((IDataPrimitive)getTag(name)).getAsByte();
		}
		catch(ClassCastException ex){}
		return (byte)0;
	}
	
	public byte getTagId(String name){
		IDataTag tag = getTag(name);
		return tag == null? (byte)0: tag.getId();
	}
	
	public byte getId(){ return 0xF; }
	
	public DataTagCompound getCompound(String name){
		try{
			if(containsKey(name, (byte)0xF)) return ((DataTagCompound)getTag(name));
		}
		catch(ClassCastException ex){}
		return new DataTagCompound();
	}
	
	public DataTagList getList(String name){
		try{
			if(containsKey(name, (byte)0xE)) return ((DataTagList)getTag(name));
		}
		catch(ClassCastException ex){}
		return new DataTagList();
	}
	
	public double getDouble(String name){
		try{
			if(containsKey(name, (byte)0x6)) return ((IDataPrimitive)getTag(name)).getAsDouble();
		}
		catch(ClassCastException ex){}
		return 0d;
	}
	
	public float getFloat(String name){
		try{
			if(containsKey(name, (byte)0x5)) return ((IDataPrimitive)getTag(name)).getAsFloat();
		}
		catch(ClassCastException ex){}
		return 0f;
	}
	
	public IDataTag copy(){
		Map<String, IDataTag> map = ContentBuilder.newHashMap();
		for(Entry<String, IDataTag> entry: entrySet()) map.put(entry.getKey(), entry.getValue());
		return new DataTagCompound(map);
	}
	
	public IDataTag getTag(String name){ return getMap().get(name); }
	
	public int hashCode(){ return getMap().hashCode(); }
	
	public int getInt(String name){
		try{
			if(containsKey(name, (byte)0x3)) return ((IDataPrimitive)getTag(name)).getAsInt();
		}
		catch(ClassCastException ex){}
		return 0;
	}
	
	public long getLong(String name){
		try{
			if(containsKey(name, (byte)0x4)) return ((IDataPrimitive)getTag(name)).getAsLong();
		}
		catch(ClassCastException ex){}
		return 0L;
	}
	
	public Map<String, IDataTag> getMap(){ return tagMap; }
	
	public Set<Entry<String, IDataTag>> entrySet(){ return getMap().entrySet(); }
	
	public Set<String> keySet(){ return getMap().keySet(); }
	
	public short getShort(String name){
		try{
			if(containsKey(name, (byte)0x2)) return ((IDataPrimitive)getTag(name)).getAsShort();
		}
		catch(ClassCastException ex){}
		return (short)0;
	}
	
	public String getString(String name){
		try{
			if(containsKey(name, (byte)0xD)) return ((DataTagString)getTag(name)).getString();
		}
		catch(ClassCastException ex){}
		return "";
	}
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void read(DataInput input, int depth) throws IOException{
		if(depth > 512) throw new ComplexTagException();
		byte id = (byte)0;
		while((id = input.readByte()) != 0x0){
			String name = input.readUTF();
			IDataTag tag = IDataTag.byId(id);
			if(tag != null){
				tag.read(input, depth + 1);
				setTag(name, tag);
			}
		}
	}
	
	public void setBoolean(String name, boolean value){ setByte(name, (byte)(value? 1: 0)); }
	
	public void setByte(String name, byte value){ setTag(name, new DataTagByte(value)); }
	
	public void setByteArray(String name, byte... array){ setTag(name, new DataTagByteArray(array)); }
	
	public void setCompound(String name, DataTagCompound tagCompound){ setTag(name, tagCompound); }
	
	public void setDouble(String name, double value){ setTag(name, new DataTagDouble(value)); }
	
	public void setDoubleArray(String name, double... array){ setTag(name, new DataTagDoubleArray(array)); }
	
	public void setFloat(String name, float value){ setTag(name, new DataTagFloat(value)); }
	
	public void setFloatArray(String name, float... array){ setTag(name, new DataTagFloatArray(array)); }
	
	public void setInt(String name, int value){ setTag(name, new DataTagInt(value)); }
	
	public void setIntArray(String name, int... array){ setTag(name, new DataTagIntArray(array)); }
	
	public void setList(String name, DataTagList tagList){ setTag(name, tagList); }
	
	public void setLong(String name, long value){ setTag(name, new DataTagLong(value)); }
	
	public void setLongArray(String name, long... array){ setTag(name, new DataTagLongArray(array)); }
	
	public void setShort(String name, short value){ setTag(name, new DataTagShort(value)); }
	
	public void setShortArray(String name, short... array){ setTag(name, new DataTagShortArray(array)); }
	
	public void setString(String name, String value){ setTag(name, new DataTagString(value)); }
	
	public void setTag(String name, IDataTag tag){ getMap().put(name, tag); }
	
	public void write(DataOutput output) throws IOException{
		for(Entry<String, IDataTag> entry: entrySet()){
			String name = entry.getKey();
			IDataTag tag = entry.getValue();
			byte id = tag.getId();
			output.writeByte(id);
			if(id == 0x0){
				output.writeUTF(name);
				tag.write(output);
			}
		}
		output.writeByte(0);
	}
	
}
