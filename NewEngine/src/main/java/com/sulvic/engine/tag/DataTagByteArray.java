package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sulvic.util.SulvicMath;

public class DataTagByteArray implements IDataList<DataTagByte>{
	
	private byte[] tagArray;
	
	DataTagByteArray(){}
	
	public DataTagByteArray(byte... array){ tagArray = array; }
	
	public DataTagByteArray(List<Byte> list){ this(getValues(list)); };
	
	private static byte[] getValues(List<Byte> list){
		byte[] array = new byte[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i).byteValue();
		return array;
	}
	
	public byte[] getValues(){ return tagArray; }
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			byte[] temp = new byte[size() + 1];
			System.arraycopy(tagArray, 0, temp, 0, index);
			temp[index] = ((IDataPrimitive)tag).getAsByte();
			System.arraycopy(tagArray, index, temp, index + 1, size() - index);
			tagArray = temp;
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagByteArray && Arrays.equals(tagArray, ((DataTagByteArray)obj).tagArray);
	}
	
	public boolean setTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			tagArray[index] = ((IDataPrimitive)tag).getAsByte();
			return true;
		}
		return false;
	}
	
	public byte getId(){ return 0x7; }
	
	public byte getListId(){ return 0x1; }
	
	public DataTagByteArray copy(){
		byte[] data = new byte[size()];
		System.arraycopy(tagArray, 0, data, 0, size());
		return new DataTagByteArray(data);
	}
	
	public DataTagByte get(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		return DataTagByte.getValue(tagArray[index]);
	}
	
	public DataTagByte remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		byte[] temp = new byte[size() - 1];
		DataTagByte result = get(index);
		System.arraycopy(tagArray, 0, temp, 0, index);
		System.arraycopy(tagArray, index + 1, temp, index, size() - index - 1);
		tagArray = temp;
		return result;
	}
	
	public DataTagByte set(int index, DataTagByte tagByte){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		DataTagByte result = get(index);
		tagArray[index] = tagByte.getAsByte();
		return result;
	}
	
	public int hashCode(){ return Arrays.hashCode(tagArray); }
	
	public int size(){ return tagArray.length; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(DataTagByte obj){
		byte[] temp = new byte[size() + 1];
		System.arraycopy(tagArray, 0, temp, 0, size());
		temp[size()] = obj.getAsByte();
		tagArray = temp;
	}
	
	public void clear(){ tagArray = new byte[0]; }
	
	public void read(DataInput input, int depth) throws IOException{
		tagArray = new byte[input.readInt()];
		input.readFully(tagArray);
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeInt(size());
		output.write(tagArray);
	}
	
}
