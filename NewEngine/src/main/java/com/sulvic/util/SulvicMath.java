package com.sulvic.util;

import java.util.Random;

public class SulvicMath{
	
	private static final double ROTATION_BASE = Math.PI / 180d;
	private static final Random RAND = new Random();
	
	private static byte max(byte value, byte value1){
		return value >= value1? value: value1;
	}
	
	private static byte min(byte value, byte value1){
		return value <= value1? value: value1;
	}
	
	private static short max(short value, short value1){
		return value >= value1? value: value1;
	}
	
	private static short min(short value, short value1){
		return value <= value1? value: value1;
	}
	
	public static boolean inRange(byte value, byte min, byte max){ return value >= min && value < max; }
	
	public static boolean inRange(double value, double min, double max){ return value >= min && value < max; }
	
	public static boolean inRange(float value, float min, float max){ return value >= min && value < max; }
	
	public static boolean inRange(int value, int min, int max){ return value >= min && value < max; }
	
	public static boolean inRange(long value, long min, long max){ return value >= min && value < max; }
	
	public static boolean inRange(short value, short min, short max){ return value >= min && value < max; }
	
	public static byte clampByte(byte value, byte min, byte max){ return value < min? min: value > max? max: value; }
	
	public static byte maxByte(byte... values){
		byte result = values[0];
		for(int i = 1; i < values.length; i++) result = max(result, values[i]);
		return result;
	}
	
	public static byte minByte(byte... values){
		byte result = values[0];
		for(int i = 1; i < values.length; i++) result = min(result, values[i]);
		return result;
	}
	
	public static byte rangedByte(Random rand, byte min, byte max){
		byte[] array = new byte[(max - min) + min];
		for(int i = 0; i < array.length; i++) array[i] = (byte)(min + (byte)i);
		return array[rand.nextInt(array.length)];
	}
	
	public static byte rangedByte(byte min, byte max){ return rangedByte(RAND, min, max); }
	
	public static double wrapByte(byte value, byte min, byte max){ return (byte)(((value - min) % (max + 1 - min) + max + 1 - min) % (max + 1 - min) + min); }
	
	public static double clampDouble(double value, double min, double max){ return value < min? min: value > max? max: value; }
	
	public static double maxDouble(double... values){
		double result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.max(result, values[i]);
		return result;
	}
	
	public static double minDouble(double... values){
		double result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.min(result, values[i]);
		return result;
	}
	
	public static double rangedDouble(Random rand, double min, double max){ return rand.nextDouble() * (max - min) + min; }
	
	public static double rangedDouble(double min, double max){ return rangedDouble(RAND, min, max); }
	
	public static double rotationDouble(double value){ return ROTATION_BASE * clampDouble(value, -180d, 180d); }
	
	public static double wrapFlaot(float value, float min, float max){ return ((value - min) % (max + 1d - min) + max + 1d - min) % (max + 1d - min) + min; }
	
	public static float clampFloat(float value, float min, float max){ return value < min? min: value > max? max: value; }
	
	public static float maxFloat(float... values){
		float result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.max(result, values[i]);
		return result;
	}
	
	public static float minFloat(float... values){
		float result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.max(result, values[i]);
		return result;
	}
		
	public static float rangedFloat(Random rand, float min, float max){ return rand.nextFloat() * (max - min) + min; }
	
	public static float rangedFloat(float min, float max){ return rangedFloat(RAND, min, max); }
	
	public static float rotationFloat(float value){ return (float)ROTATION_BASE * clampFloat(value, -180f, 180f); }
	
	public static float wrapFloat(float value, float min, float max){ return ((value - min) % (max + 1f - min) + max + 1f - min) % (max + 1f - min) + min; }
	
	public static int ceilDoubleInt(double value){
		int temp = (int)value;
		return value < temp? temp + 1: temp;
	}
	
	public static int ceilFloatInt(float value){
		int temp = (int)value;
		return value < temp? temp + 1: temp;
	}
	
	public static int clampInt(int value, int min, int max){ return value < min? min: value > max? max: value; }
	
	public static int floorDoubleInt(double value){
		int temp = (int)value;
		return value < temp? temp - 1: temp;
	}
	
	public static int floorFloatInt(float value){
		int temp = (int)value;
		return value < temp? temp - 1: temp;
	}
	
	public static int maxInt(int... values){
		int result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.max(result, values[i]);
		return result;
	}
	
	public static int minInt(int... values){
		int result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.min(result, values[i]);
		return result;
	}
	
	public static int rangedInt(Random rand, int min, int max){ return rand.nextInt(max - min) + min; }
	
	public static int rangedInt(int min, int max){ return rangedInt(RAND, min, max); }
	
	public static int wrapInt(int value, int min, int max){ return ((value - min) % (max + 1 - min) + max + 1 - min) % (max + 1 - min) + min; }
	
	public static long ceilDoubleLong(double value){
		long temp = (long)value;
		return value < temp? temp + 1L: temp;
	}
	
	public static long ceilFloatLong(float value){
		long temp = (long)value;
		return value < temp? temp + 1L: temp;
	}
	
	public static long clampLong(long value, long min, long max){ return value < min? min: value > max? max: value; }
	
	public static long floorDoubleLong(double value){
		long temp = (long)value;
		return value < temp? temp - 1L: temp;
	}
	
	public static long floorFloatLong(float value){
		long temp = (long)value;
		return value < temp? temp - 1L: temp;
	}
	
	public static long maxLong(long... values){
		long result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.max(result, values[i]);
		return result;
	}
	
	public static long minLong(long... values){
		long result = values[0];
		for(int i = 1; i < values.length; i++) result = Math.min(result, values[i]);
		return result;
	}
	
	public static long rangedLong(Random rand, long min, long max){ return rand.nextLong() * (max - min) + min; }
	
	public static long rangedLong(long min, long max){ return rangedLong(RAND, min, max); }
	
	public static long wrapLong(long value, long min, long max){ return ((value - min) % (max + 1L - min) + max + 1L - min) % (max + 1L - min) + min; }
	
	public static short clampShort(short value, short min, short max){ return value < min? min: value > max? max: value; }
	
	public static short maxShort(short... values){
		short result = values[0];
		for(int i = 1; i < values.length; i++) result = max(result, values[i]);
		return result;
	}
	
	public static short minShort(short... values){
		short result = values[0];
		for(int i = 1; i < values.length; i++) result = min(result, values[i]);
		return result;
	}
	
	public static short rangedShort(Random rand, short min, short max){
		short[] array = new short[(max - min) + min];
		for(int i = 0; i < array.length; i++) array[i] = (short)(min + (short)i);
		return array[rand.nextInt(array.length)];
	}
	
	public static short rangedShort(short min, short max){ return rangedShort(RAND, min, max); }
	
	public static short wrapLong(short value, short min, short max){ return (short)(((value - min) % (max + 1 - min) + max + 1 - min) % (max + 1 - min) + min); }
	
}
