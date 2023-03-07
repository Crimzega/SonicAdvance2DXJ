package com.sulvic.util;

import java.util.*;

@SuppressWarnings({"unchecked"})
public class ContentBuilder{
	
	public static <T> ArrayList<T> newArrayList(){ return new ArrayList<>(); }
	
	public static <T> ArrayList<T> newArrayList(T... objs){
		ArrayList<T> result = newArrayList();
		for(T obj: objs) result.add(obj);
		return result;
	}
	
	public static <T> HashSet<T> newHashSet(){ return new HashSet<T>(); }
	
	public static <T> HashSet<T> newHashSet(T... objs){
		HashSet<T> result = newHashSet();
		for(T obj: objs) result.add(obj);
		return result;
	}
	
	public static <K, V> HashMap<K, V> newHashMap(){ return new HashMap<>(); }
	
	public static <K, V> HashMap<K, V> newHashMap(int size){ return new HashMap<>(size); }
	
	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(){ return new IdentityHashMap<>(); }
	
	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int max){ return new IdentityHashMap<>(max); }
	
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(){ return new LinkedHashMap<>(); }
	
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int size){ return new LinkedHashMap<>(size); }
	
	public static <K, V> TreeMap<K, V> newTreeMap(){ return new TreeMap<>(); }
	
}
