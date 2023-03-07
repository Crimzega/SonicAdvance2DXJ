package com.sulvic.io;

public class BytesHelper{
	
	public static byte[] getByteArray(double value){ return getByteArray(Double.doubleToLongBits(value)); }
	
	public static byte[] getByteArray(float value){ return getByteArray(Float.floatToIntBits(value)); }
	
	public static byte[] getByteArray(long value){
		return new byte[]{
			(byte)((value & 0xFF00000000000000L) >> 56L),
			(byte)((value & 0x00FF000000000000L) >> 48L),
			(byte)((value & 0x0000FF0000000000L) >> 40L),
			(byte)((value & 0x000000FF00000000L) >> 32L),
			(byte)((value & 0x00000000FF000000L) >> 24L),
			(byte)((value & 0x0000000000FF0000L) >> 16L),
			(byte)((value & 0x000000000000FF00L) >> 8L),
			(byte)(value & 0x00000000000000FFL),
		};
	}
	
	public static byte[] getByteArray(int value){
		return new byte[]{
			(byte)((value & 0xFF000000) >> 24),
			(byte)((value & 0x00FF0000) >> 16),
			(byte)((value & 0x0000FF00) >> 8),
			(byte)(value & 0x000000FF),
		};
	}
	
	public static byte[] getByteArray(short value){
		return new byte[]{
			(byte)((value & 0xFF00) >> 8),
			(byte)(value & 0x00FF),
		};
	}
	
	public static double getDouble(byte[] buffer){ return Double.longBitsToDouble(getLong(buffer)); }
	
	public static float getFloat(byte[] buffer){ return Float.intBitsToFloat(getInt(buffer)); }
	
	public static int getInt(byte[] buffer){
		int result = 0;
		if(buffer == null) return result;
		if(buffer.length != 4) return result;
		for(int i = 0; i < buffer.length; i++) result |= (buffer[i] & 0xFF) << 8 * i;
		return result;
	}
	
	public static long getLong(byte[] buffer){
		long result = 0;
		if(buffer == null) return result;
		if(buffer.length != 8) return result;
		for(int i = 0; i < buffer.length; i++) result |= (buffer[i] & 0xFFL) << 8 * i;
		return result;
	}
	
	public static short getShort(byte[] buffer){
		short result = 0;
		if(buffer == null) return result;
		if(buffer.length != 2) return result;
		for(int i = 0; i < buffer.length; i++) result |= (buffer[i] & 0xFF) << 8 * i;
		return result;
	}
	
}
