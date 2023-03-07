package com.sulvic.lib;

@SuppressWarnings({"unchecked"})
public class DoubleValueBasic<V, V1> implements DoubleValueSet<V, V1>{
	
	V firstValue;
	V1 secondValue;
	
	public DoubleValueBasic(V value, V1 value1){
		firstValue = value;
		secondValue = value1;
	}
	
	public V getFirstValue(){ return firstValue; }
	
	public V setFirstValue(V value){
		V origValue = firstValue;
		firstValue = value;
		return origValue;
	}
	
	public V1 getSecondValue(){ return secondValue; }
	
	public V1 setSecondValue(V1 value1){
		V1 origValue1 = secondValue;
		secondValue = value1;
		return origValue1;
	}
	
	public boolean equals(Object obj){ return obj instanceof DoubleValueSet? hasValues(obj): super.equals(obj); }
	
	public boolean hasValues(Object values){
		if(values instanceof DoubleValueSet){
			DoubleValueSet<V, V1> valueSet = (DoubleValueSet<V, V1>)values;
			return hasValues(valueSet.getFirstValue(), valueSet.getSecondValue());
		}
		return false;
	}
	
	public boolean hasValues(Object value, Object value1){ return firstValue.equals(value) && secondValue.equals(value1); }
	
	public int hashCode(){ return HashCore.create(11, 21).append(firstValue).append(secondValue).asHash(); }
	
}
