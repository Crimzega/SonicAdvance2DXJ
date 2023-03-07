package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sulvic.util.SulvicMath;

public class DataTagShortArray implements IDataList<DataTagShort>{
	
	private short[] tagArray;
	
	DataTagShortArray(){}
	
	public DataTagShortArray(short... array){ tagArray = array; }
	
	public DataTagShortArray(List<Short> list){ this(getValues(list)); }
	
	private static short[] getValues(List<Short> list){
		short[] array = new short[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i).shortValue();
		return array;
	}
	
	public short[] getValues(){ return tagArray; }
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			short[] temp = new short[size() + 1];
			System.arraycopy(tagArray, 0, temp, 0, size());
			temp[index] = ((IDataPrimitive)tag).getAsShort();
			System.arraycopy(tagArray, index, temp, index + 1, size() - index);
			tagArray = temp;
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagShortArray && Arrays.equals(tagArray, ((DataTagShortArray)obj).tagArray);
	}
	
	public boolean setTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			tagArray[index] = ((IDataPrimitive)tag).getAsShort();
			return true;
		}
		return false;
	}
	
	public byte getId(){ return 0x8; }
	
	public byte getListId(){ return 0x2; }
	
	public DataTagShortArray copy(){
		short[] data = new short[size()];
		System.arraycopy(tagArray, 0, data, 0, size());
		return new DataTagShortArray(data);
	}
	
	public DataTagShort get(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		return DataTagShort.getValue(tagArray[index]);
	}
	
	public DataTagShort remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		short[] temp = new short[size() - 1];
		DataTagShort result = get(index);
		System.arraycopy(tagArray, 0, temp, 0, index);
		System.arraycopy(tagArray, index + 1, temp, index, size() - index - 1);
		tagArray = temp;
		return result;
	}
	
	public DataTagShort set(int index, DataTagShort tagShort){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		DataTagShort result = get(index);
		tagArray[index] = tagShort.getAsShort();
		return result;
	}
	
	public int hashCode(){ return Arrays.hashCode(tagArray); }
	
	public int size(){ return tagArray.length; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(DataTagShort obj){
		short[] temp = new short[size() + 1];
		System.arraycopy(tagArray, 0, temp, 0, size());
		temp[size()] = obj.getAsShort();
		tagArray = temp;
	}
	
	public void clear(){ tagArray = new short[0]; }
	
	public void read(DataInput input, int depth) throws IOException{
		tagArray = new short[input.readInt()];
		for(int i = 0; i < size(); i++) tagArray[i] = input.readShort();
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeInt(size());
		for(int i = 0; i < size(); i++) output.writeShort(tagArray[i]);
	}
	
}
