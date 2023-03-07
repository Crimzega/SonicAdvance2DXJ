package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sulvic.util.SulvicMath;

public class DataTagLongArray implements IDataList<DataTagLong>{
	
	private long[] tagArray;
	
	DataTagLongArray(){}
	
	public DataTagLongArray(long... array){ tagArray = array; }
	
	public DataTagLongArray(List<Long> list){ this(getValues(list)); }
	
	private static long[] getValues(List<Long> list){
		long[] array = new long[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i).longValue();
		return array;
	}
	
	public long[] getValues(){ return tagArray; }
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			long[] temp = new long[size()];
			System.arraycopy(tagArray, 0, temp, 0, size());
			temp[size()] = ((IDataPrimitive)tag).getAsLong();
			System.arraycopy(tagArray, index, temp, index + 1, size() - index);
			tagArray = temp;
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagLongArray && Arrays.equals(tagArray, ((DataTagLongArray)obj).tagArray);
	}
	
	public boolean setTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			tagArray[index] = ((IDataPrimitive)tag).getAsLong();
			return true;
		}
		return false;
	}
	
	public byte getId(){ return 0xA; }
	
	public byte getListId(){ return 0x4; }
	
	public DataTagLongArray copy(){
		long[] data = new long[size()];
		System.arraycopy(tagArray, 0, data, 0, size());
		return new DataTagLongArray();
	}
	
	public DataTagLong get(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		return DataTagLong.getValue(tagArray[index]);
	}
	
	public DataTagLong remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		long[] temp = new long[size() - 1];
		DataTagLong result = get(index);
		System.arraycopy(tagArray, 0, temp, 0, index);
		System.arraycopy(tagArray, index + 1, temp, index, size() - index - 1);
		tagArray = temp;
		return result;
	}
	
	public DataTagLong set(int index, DataTagLong tagInt){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		DataTagLong result = get(index);
		tagArray[index] = tagInt.getAsInt();
		return result;
	}
	
	public int hashCode(){ return Arrays.hashCode(tagArray); }
	
	public int size(){ return tagArray.length; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(DataTagLong obj){
		long[] temp = new long[size()];
		System.arraycopy(tagArray, 0, temp, 0, size());
		temp[size()] = obj.getAsLong();
		tagArray = temp;
	}
	
	public void clear(){ tagArray = new long[0]; }
	
	public void read(DataInput input, int depth) throws IOException{
		tagArray = new long[input.readInt()];
		for(int i = 0; i < size(); i++) tagArray[i] = input.readLong();
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeInt(size());
		for(int i = 0; i < size(); i++) output.writeLong(tagArray[i]);
	}
	
}
