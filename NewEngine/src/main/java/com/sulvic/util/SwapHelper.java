package com.sulvic.util;

public class SwapHelper{
	
	public static byte[] swapBytes(byte[] array){
		if(array == null) return null;
		byte[] result = new byte[array.length];
		for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
		return result;
	}
	
	public static double swap(double value){ return Double.longBitsToDouble(swap(Double.doubleToLongBits(value))); }
	
	public static float swap(float value){ return Float.intBitsToFloat(swap(Float.floatToIntBits(value))); }
	
	public static int swap(int value){ return (value & 0xFF000000) >> 24 | (value & 0x00FF0000) >> 8 | (value & 0x0000FF00) << 8 | (value & 0x000000FF) << 24; }
	
	public static long swap(long value){
		return (value & 0xFF00000000000000L) >> 56L | (value & 0x00FF000000000000L) >> 40L | (value & 0x0000FF0000000000L) >> 24L | (value & 0x000000FF00000000L) >> 8L
			| (value & 0x00000000FF000000L) << 8L | (value & 0x0000000000FF0000L) << 24L | (value & 0x000000000000FF00L) << 40L | (value & 0x00000000000000FFL) << 56L;
	}
	
	public static short swap(short value){ return (short)((value & 0xFF00) >> 8 | (value & 0x00FF) << 8); }
	
}
