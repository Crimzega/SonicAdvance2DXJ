package com.sulvic.engine.tag;

import java.io.*;
import java.util.*;

import com.sulvic.engine.exception.*;
import com.sulvic.util.ContentBuilder;
import com.sulvic.util.SulvicMath;

public class DataTagList implements IDataList<IDataTag>, Iterable<IDataTag>{
	
	private List<IDataTag> tagList;
	private byte validTagId;
	
	public DataTagList(){ this(ContentBuilder.newArrayList(), (byte)0x0); }
	
	public DataTagList(List<IDataTag> list, byte id){
		tagList = list;
		validTagId = id;
	}
	
	private boolean update(IDataTag tag){
		if(tag.getId() == 0) return false;
		if(validTagId == 0){
			validTagId = tag.getId();
			return true;
		}
		return tag.getId() == validTagId;
	}
	
	private void update(){ if(isEmpty()) validTagId = 0; }
	
	public byte[] getByteArray(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x7) return ((DataTagByteArray)tag).getValues();
		return new byte[0];
	}
	
	public double[] getDoubleArray(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0xC) return ((DataTagDoubleArray)tag).getValues();
		return new double[0];
	}
	
	public float[] getFloatArray(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0xB) return ((DataTagFloatArray)tag).getValues();
		return new float[0];
	}
	
	public int[] getIntArray(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x9) return ((DataTagIntArray)tag).getValues();
		return new int[0];
	}
	
	public long[] getLongArray(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0xA) return ((DataTagLongArray)tag).getValues();
		return new long[0];
	}
	
	public short[] getShortArray(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x8) return ((DataTagShortArray)tag).getValues();
		return new short[0];
	}
	
	public boolean addTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(update(tag)){
			getList().add(index, tag);
			return true;
		}
		return false;
	}
	
	public boolean equals(Object obj){
		if(obj == this) return true;
		if(obj instanceof DataTagList){
			DataTagList refTagList = (DataTagList)obj;
			return validTagId == refTagList.validTagId && getList().equals(refTagList.getList());
		}
		return false;
	}
	
	public boolean getBoolean(int index){ return getByte(index) == 0x1; }
	
	public boolean isEmpty(){ return getList().isEmpty(); }
	
	public boolean setTag(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		if(update(tag)){
			getList().set(index, tag);
			return true;
		}
		return false;
	}
	
	public byte getByte(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x1) return ((IDataPrimitive)tag).getAsByte();
		return (byte)0;
	}
	
	public byte getId(){ return 0xE; }
	
	public byte getListId(){ return validTagId; }
	
	public int hashCode(){ return getList().hashCode(); }
	
	public int size(){ return getList().size(); }
	
	public DataTagCompound getCompound(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0xF) return ((DataTagCompound)tag);
		return new DataTagCompound();
	}
	
	public DataTagList getList(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0xE) return ((DataTagList)tag);
		return new DataTagList();
	}
	
	public DataTagList copy(){
		DataTagList result = new DataTagList();
		for(IDataTag tag: this) result.add(tag.copy());
		return result;
	}
	
	public double getDouble(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x6) return ((IDataPrimitive)tag).getAsDouble();
		return 0d;
	}
	
	public float getFloat(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x5) return ((IDataPrimitive)tag).getAsByte();
		return 0f;
	}
	
	public IDataTag get(int index){ return getList().get(index); }
	
	public IDataTag set(int index, IDataTag tag){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag result = get(index);
		if(!setTag(index, tag)) throw new MismatchTagException("Trying to add an DataTag of a different type: (Expected: %d, Given: %d)", validTagId, tag.getId());
		return result;
	}
	
	public IDataTag remove(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag result = getList().remove(index);
		update();
		return result;
	}
	
	public int getInt(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x3) return ((IDataPrimitive)tag).getAsInt();
		return 0;
	}
	
	public Iterator<IDataTag> iterator(){ return getList().iterator(); }
	
	public List<IDataTag> getList(){ return tagList; }
	
	public long getLong(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x4) return ((IDataPrimitive)tag).getAsLong();
		return 0L;
	}
	
	public short getShort(int index){
		if(!SulvicMath.inRange(index, 0, size())) throw new IndexOutOfBoundsException();
		IDataTag tag = get(index);
		if(tag.getId() == 0x2) return ((IDataPrimitive)tag).getAsShort();
		return (short)0;
	}
	
	public String toString(){ return TagBuilder.tagString(this); }
	
	public void add(IDataTag tag){
		if(!addTag(size() - 1, tag)) throw new MismatchTagException("Trying to add an DataTag of a different type: (Expected: %d, Given: %d)", validTagId, tag.getId());
	}
	
	public void clear(){
		getList().clear();
		validTagId = 0;
	}
	
	public void read(DataInput input, int depth) throws IOException{
		if(depth > 512) throw new ComplexTagException();
		byte id = input.readByte();
		int length = input.readInt();
		if(id == 0 && length > 0) throw new MissingTagException();
		for(int i = 0; i < length; i++){
			IDataTag tag = IDataTag.byId(id);
			tag.read(input, depth + 1);
			getList().add(tag);
		}
	}
	
	public void write(DataOutput output) throws IOException{
		output.writeByte(validTagId == 0 || isEmpty()? 0: getList().get(0).getId());
		output.writeInt(size());
		for(IDataTag tag: this) tag.write(output);
	}
	
}
