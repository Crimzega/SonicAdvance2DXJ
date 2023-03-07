package com.sulvic.engine.tag;

public interface IDataList<T extends IDataTag> extends IDataTag{
	
	boolean setTag(int index, IDataTag tag);
	
	boolean addTag(int index, IDataTag tag);
	
	byte getListId();
	
	int size();
	
	T get(int index);
	
	T remove(int index);
	
	T set(int index, T obj);
	
	void add(T obj);
	
	void clear();
	
}
