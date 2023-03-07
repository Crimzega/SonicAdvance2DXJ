package com.sulvic.lib;

@SuppressWarnings({"unchecked"})
public class DoubleKeyBasic<K, K1> implements DoubleKeySet<K, K1>{
	
	K firstKey;
	K1 secondKey;
	
	public DoubleKeyBasic(K key, K1 key1){
		firstKey = key;
		secondKey = key1;
	}
	
	public K getFirstKey(){ return firstKey; }
	
	public K1 getSecondKey(){ return secondKey; }
	
	public boolean hasKeys(Object keys){
		if(keys instanceof DoubleKeySet){
			DoubleKeySet<K, K1> keySet = (DoubleKeySet<K, K1>)keys;
			return hasKeys(keySet.getFirstKey(), keySet.getSecondKey());
		}
		return false;
	}
	
	public boolean hasKeys(Object key, Object key1){ return firstKey.equals(key) && secondKey.equals(key1); }
	
	public boolean equals(Object obj){ return obj instanceof DoubleKeySet? hasKeys(obj): super.equals(obj); }
	
	public int hashCode(){ return HashCore.create(9, 25).append(firstKey).append(secondKey).asHash(); }
	
}
