package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sulvic.util.SulvicMath;

public class DataTagFloatArray implements IDataList<DataTagFloat>{
	
	private float[] tagArray;
	
	DataTagFloatArray(){}
	
	public DataTagFloatArray(float... array){ tagArray = array; }
	
	public DataTagFloatArray(List<Float> list){ this(getValues(list)); }
	
	private static float[] getValues(List<Float> list){
		float[] array = new float[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i).longValue();
		return array;
	}
	
	public float[] getValues(){ return tagArray; }
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			float[] temp = new float[size()];
			System.arraycopy(tagArray, 0, temp, 0, size());
			temp[size()] = ((IDataPrimitive)tag).getAsFloat();
			System.arraycopy(tagArray, index, temp, index + 1, size() - index);
			tagArray = temp;
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagFloatArray && Arrays.equals(tagArray, ((DataTagFloatArray)obj).tagArray);
	}
	
	public boolean setTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			tagArray[index] = ((IDataPrimitive)tag).getAsFloat();
			return true;
		}
		return false;
	}
	
	public byte getId(){ return 0xB; }
	
	public byte getListId(){ return 0x5; }
	
	public DataTagFloatArray copy(){
		float[] data = new float[size()];
		System.arraycopy(tagArray, 0, data, 0, size());
		return new DataTagFloatArray();
	}
	
	public DataTagFloat get(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		return DataTagFloat.getValue(tagArray[index]);
	}
	
	public DataTagFloat remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		float[] temp = new float[size() - 1];
		DataTagFloat result = get(index);
		System.arraycopy(tagArray, 0, temp, 0, index);
		System.arraycopy(tagArray, index + 1, temp, index, size() - index - 1);
		tagArray = temp;
		return result;
	}
	
	public DataTagFloat set(int index, DataTagFloat tagInt){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		DataTagFloat result = get(index);
		tagArray[index] = tagInt.getAsInt();
		return result;
	}
	
	public int hashCode(){ return Arrays.hashCode(tagArray); }
	
	public int size(){ return tagArray.length; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(DataTagFloat obj){
		float[] temp = new float[size()];
		System.arraycopy(tagArray, 0, temp, 0, size());
		temp[size()] = obj.getAsFloat();
		tagArray = temp;
	}
	
	public void clear(){ tagArray = new float[0]; }
	
	public void read(DataInput input, int depth) throws IOException{
		tagArray = new float[input.readInt()];
		for(int i = 0; i < size(); i++) tagArray[i] = input.readFloat();
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeInt(size());
		for(int i = 0; i < size(); i++) output.writeFloat(tagArray[i]);
	}
	
}
