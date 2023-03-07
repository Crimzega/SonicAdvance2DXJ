package com.sulvic.engine.util;

import java.util.Iterator;

import com.sulvic.engine.exception.RegistryException;
import com.sulvic.util.SulvicCollections;
import com.sulvic.util.SulvicMath;

@SuppressWarnings({"unchecked"})
public class RegistryList<E> implements Cloneable, Iterable<RegistryList.Entry<E>>{
	
	private Entry<E>[] theEntries;
	
	public RegistryList(){ theEntries = new Entry[0]; }
	
	private int clampedId(int id){ return SulvicMath.clampInt(id, 0, theEntries.length - 1); }
	
	public E getObject(int id){ return theEntries[clampedId(id)].getObject(); }
	
	public E getObject(String name){
		for(Entry<E> entry: theEntries) if(entry.hasName(name)) return entry.getObject();
		return null;
	}
	
	public boolean addObject(E obj, String name){
		if(isRegistered(obj)) throw new RegistryException(getName(obj));
		theEntries = SulvicCollections.mergeArrays(theEntries, new RegistryList.Entry<>(obj, name));
		return false;
	}
	
	public boolean isRegistered(E obj){
		for(Entry<E> entry: theEntries) if(entry.hasObject(obj)) return true;
		return false;
	}
	
	public int getId(E obj){
		int result = -1;
		for(Entry<E> entry: theEntries){
			if(entry.hasObject(obj)) break;
			result++;
		}
		return result;
	}
	
	public int getId(String name){
		int result = -1;
		for(Entry<E> entry: theEntries){
			if(entry.hasName(name)) break;
			result++;
		}
		return result;
	}
	
	public int size(){ return theEntries.length; }
	
	public Iterator<RegistryList.Entry<E>> iterator(){ return new RegistryIterator(); }
	
	public String getName(E obj){
		for(Entry<E> entry: theEntries) if(entry.hasObject(obj)) return entry.getName();
		return null;
	}
	
	public String getName(int id){ return theEntries[clampedId(id)].getName(); }
	
	public static class Entry<E> implements Cloneable{
		
		private E theObject;
		private String entryName;
		
		public Entry(E obj, String name){
			theObject = obj;
			entryName = name;
		}
		
		public E getObject(){ return theObject; }
		
		public boolean hasName(String name){ return getName().equals(name); }
		
		public boolean hasObject(E obj){ return getObject().equals(obj); }
		
		public String getName(){ return entryName; }
		
	}
	
	public class RegistryIterator implements Iterator<RegistryList.Entry<E>>{
		
		private int currIndex;
		
		public RegistryIterator(){ currIndex = 0; }
		
		public boolean hasNext(){ return currIndex != theEntries.length; }
		
		public RegistryList.Entry<E> next(){ return theEntries[currIndex++]; }
		
	}
	
}
