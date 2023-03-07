package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sulvic.util.SulvicMath;

public class DataTagIntArray implements IDataList<DataTagInt>{
	
	private int[] tagArray;
	
	DataTagIntArray(){}
	
	public DataTagIntArray(int... array){ tagArray = array; }
	
	public DataTagIntArray(List<Integer> list){ this(getValues(list)); }
	
	private static int[] getValues(List<Integer> list){
		int[] array = new int[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i).intValue();
		return array;
	}
	
	public int[] getValues(){ return tagArray; }
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			int[] temp = new int[size()];
			System.arraycopy(tagArray, 0, temp, 0, size());
			temp[size()] = ((IDataPrimitive)tag).getAsInt();
			System.arraycopy(tagArray, index, temp, index + 1, size() - index);
			tagArray = temp;
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagIntArray && Arrays.equals(tagArray, ((DataTagIntArray)obj).tagArray);
	}
	
	public boolean setTag(int index, IDataTag tag){ // TODO Auto-generated method stub
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			tagArray[index] = ((IDataPrimitive)tag).getAsInt();
			return true;
		}
		return false;
	}
	
	public byte getId(){ return 0x9; }
	
	public byte getListId(){ return 0x3; }
	
	public DataTagIntArray copy(){
		int[] data = new int[size()];
		System.arraycopy(tagArray, 0, data, 0, size());
		return new DataTagIntArray();
	}
	
	public DataTagInt get(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		return DataTagInt.getValue(tagArray[index]);
	}
	
	public DataTagInt remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		int[] temp = new int[size() - 1];
		DataTagInt result = get(index);
		System.arraycopy(tagArray, 0, temp, 0, index);
		System.arraycopy(tagArray, index + 1, temp, index, size() - index - 1);
		tagArray = temp;
		return result;
	}
	
	public DataTagInt set(int index, DataTagInt tagInt){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		DataTagInt result = get(index);
		tagArray[index] = tagInt.getAsInt();
		return result;
	}
	
	public int hashCode(){ return Arrays.hashCode(tagArray); }
	
	public int size(){ return tagArray.length; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(DataTagInt obj){
		int[] temp = new int[size()];
		System.arraycopy(tagArray, 0, temp, 0, size());
		temp[size()] = obj.getAsInt();
		tagArray = temp;
	}
	
	public void clear(){ tagArray = new int[0]; }
	
	public void read(DataInput input, int depth) throws IOException{
		tagArray = new int[input.readInt()];
		for(int i = 0; i < size(); i++) tagArray[i] = input.readInt();
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeInt(size());
		for(int i = 0; i < size(); i++) output.writeInt(tagArray[i]);
	}
	
}
