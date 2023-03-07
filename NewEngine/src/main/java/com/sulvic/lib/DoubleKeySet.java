package com.sulvic.lib;

import java.util.Map;

public interface DoubleKeySet<K, K1> extends Cloneable{
	
	static <K, K1, V> boolean mapContainsKey(Map<DoubleKeySet<K, K1>, V> map, K key, K1 key1){
		for(DoubleKeySet<K, K1> keySet: map.keySet()) if(keySet.hasKeys(key, key1)) return true;
		return false;
	}
	
	static <K, K1, V> V betMapObject(Map<DoubleKeySet<K, K1>, V> map, K key, K1 key1){
		if(mapContainsKey(map, key, key1)){
			for(Map.Entry<DoubleKeySet<K, K1>, V> entrySet: map.entrySet()){
				DoubleKeySet<K, K1> keySet = entrySet.getKey();
				if(keySet.hasKeys(key, key1)) return entrySet.getValue();
			}
			return null;
		}
		return null;
	}
	
	K getFirstKey();
	
	K1 getSecondKey();
	
	boolean hasKeys(Object keys);
	
	boolean hasKeys(Object key, Object key1);
	
	int hashCode();
	
}
