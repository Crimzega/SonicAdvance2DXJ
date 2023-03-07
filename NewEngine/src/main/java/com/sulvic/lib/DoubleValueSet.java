package com.sulvic.lib;

public interface DoubleValueSet<V, V1> {
	
	V getFirstValue();
	
	V setFirstValue(V value);
	
	V1 getSecondValue();
	
	boolean equals(Object obj);
	
	boolean hasValues(Object values);
	
	boolean hasValues(Object value, Object value1);
	
	V1 setSecondValue(V1 value1);
	
	int hashCode();
	
}
