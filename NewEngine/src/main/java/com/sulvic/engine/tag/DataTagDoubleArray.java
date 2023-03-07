package com.sulvic.engine.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sulvic.util.SulvicMath;

public class DataTagDoubleArray implements IDataList<DataTagDouble>{
	
	private double[] tagArray;
	
	DataTagDoubleArray(){}
	
	public DataTagDoubleArray(double... array){ tagArray = array; }
	
	public DataTagDoubleArray(List<Double> list){ this(getValues(list)); }
	
	private static double[] getValues(List<Double> list){
		double[] array = new double[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i).doubleValue();
		return array;
	}
	
	public double[] getValues(){ return tagArray; }
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			double[] temp = new double[size()];
			System.arraycopy(tagArray, 0, temp, 0, size());
			temp[size()] = ((IDataPrimitive)tag).getAsDouble();
			System.arraycopy(tagArray, index, temp, index + 1, size() - index);
			tagArray = temp;
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		return obj instanceof DataTagDoubleArray && Arrays.equals(tagArray, ((DataTagDoubleArray)obj).tagArray);
	}
	
	public boolean setTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(tag instanceof IDataPrimitive){
			tagArray[index] = ((IDataPrimitive)tag).getAsDouble();
			return true;
		}
		return false;
	}
	
	public byte getId(){ return 0xC; }
	
	public byte getListId(){ return 0x6; }
	
	public DataTagDoubleArray copy(){
		double[] data = new double[size()];
		System.arraycopy(tagArray, 0, data, 0, size());
		return new DataTagDoubleArray();
	}
	
	public DataTagDouble get(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		return DataTagDouble.getValue(tagArray[index]);
	}
	
	public DataTagDouble remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		double[] temp = new double[size() - 1];
		DataTagDouble result = get(index);
		System.arraycopy(tagArray, 0, temp, 0, index);
		System.arraycopy(tagArray, index + 1, temp, index, size() - index - 1);
		tagArray = temp;
		return result;
	}
	
	public DataTagDouble set(int index, DataTagDouble tagInt){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		DataTagDouble result = get(index);
		tagArray[index] = tagInt.getAsInt();
		return result;
	}
	
	public int hashCode(){ return Arrays.hashCode(tagArray); }
	
	public int size(){ return tagArray.length; }
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(DataTagDouble obj){
		double[] temp = new double[size()];
		System.arraycopy(tagArray, 0, temp, 0, size());
		temp[size()] = obj.getAsDouble();
		tagArray = temp;
	}
	
	public void clear(){ tagArray = new double[0]; }
	
	public void read(DataInput input, int depth) throws IOException{
		tagArray = new double[input.readInt()];
		for(int i = 0; i < size(); i++) tagArray[i] = input.readDouble();
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeInt(size());
		for(int i = 0; i < size(); i++) output.writeDouble(tagArray[i]);
	}
	
}
