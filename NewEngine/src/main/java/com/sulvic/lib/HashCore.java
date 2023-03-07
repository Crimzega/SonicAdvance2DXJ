package com.sulvic.lib;

import java.util.Random;

@SuppressWarnings("unused")
public class HashCore{
	
	private static final Random RAND = new Random(0x15612E116ABC50F8L);
	private int initOdd, multOdd;
	private int hashResult;
	
	private HashCore(int init, int mult){
		hashResult = initOdd = init % 2 == 0? init + (RAND.nextBoolean()? -1: 1): init;
		multOdd = mult % 2 == 0? mult + (RAND.nextBoolean()? -1: 1): mult;
	}
	
	public static HashCore create(int init, int mult){ return new HashCore(init, mult); }
	
	public HashCore append(Object obj){
		hashResult = multOdd * hashResult + (obj == null? 0: obj.hashCode());
		return this;
	}
	
	public int asHash(){ return hashResult; }
	
}
