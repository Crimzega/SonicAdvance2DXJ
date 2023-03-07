package com.sulvic.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SulvicCollections{
	
	public static <T> T[] mergeArrays(T[] array, T... extras){
		if(array == null) return extras;
		T[] result = (T[])new Object[array.length + extras.length];
		System.arraycopy(array, 0, result, 0, array.length);
		System.arraycopy(extras, 0, result, array.length, extras.length);
		return result;
	}
	
	public static <T> T[] remove(T[] array, int index){
		T[] result = (T[])new Object[array.length - 1];
		System.arraycopy(array, 0, result, 0, index);
		System.arraycopy(array, index + 1, result, index, result.length - index);
		return result;
	}
	
	public static <T> T[] reverseArray(T... array){
		T[] result = (T[])new Object[array.length];
		for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
		return result;
	}
	
	public static <T> T[] subArray(T[] array, int start, int end){
		if(array == null) return null;
		start = SulvicMath.maxInt(start, 0);
		end = SulvicMath.minInt(end, array.length - 1);
		int newSize = end - start;
		if(newSize <= 0) return (T[])new Object[0];
		T[] result = (T[])new Object[newSize];
		System.arraycopy(array, start, result, 0, end);
		return result;
	}
	
	public static <K, V> K randomKey(Map<K, V> map){ return randomObject(map.keySet()); }
	
	public static <K, V> V randomObject(Map<K, V> map){ return randomObject(map.entrySet()).getValue(); }
	
	public static <T> T clampedObject(T[] array, int index){ return array[SulvicMath.clampInt(index, 0, array.length - 1)]; }
	
	public static <T> T clampedValue(Collection<T> collection, int index){
		int newIndex = SulvicMath.clampInt(index, 0, collection.size() - 1);
		T result = null;
		for(T item: collection){
			if(newIndex == 0){
				result = item;
				break;
			}
			newIndex--;
		}
		return result;
	}
	
	public static <T> T randomObject(T[] array){ return array[SulvicMath.rangedInt(0, array.length - 1)]; }
	
	public static <T> T randomObject(Collection<T> collection){
		int index = SulvicMath.rangedInt(0, collection.size() - 1);
		T result = null;
		for(T item: collection){
			if(index == 0){
				result = item;
				break;
			}
			index--;
		}
		return result;
	}
	
	public static class Primitives{
		
		public static boolean[] mergeArrays(boolean[] array, boolean... extras){
			if(array == null) return extras;
			boolean[] result = new boolean[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static boolean[] remove(boolean[] array, int index){
			boolean[] result = new boolean[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static boolean[] reverseArray(boolean... array){
			boolean[] result = new boolean[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static byte[] mergeArrays(byte[] array, byte... extras){
			if(array == null) return extras;
			byte[] result = new byte[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static byte[] remove(byte[] array, int index){
			byte[] result = new byte[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static byte[] reverseArray(byte... array){
			byte[] result = new byte[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static char[] mergeArrays(char[] array, char... extras){
			if(array == null) return extras;
			char[] result = new char[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static char[] remove(char[] array, int index){
			char[] result = new char[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static char[] reverseArray(char... array){
			char[] result = new char[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static double[] mergeArrays(double[] array, double... extras){
			if(array == null) return extras;
			double[] result = new double[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static double[] remove(double[] array, int index){
			double[] result = new double[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static double[] reverseArray(double... array){
			double[] result = new double[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static float[] mergeArrays(float[] array, float... extras){
			if(array == null) return extras;
			float[] result = new float[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static float[] remove(float[] array, int index){
			float[] result = new float[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static float[] reverseArray(float... array){
			float[] result = new float[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static int[] mergeArrays(int[] array, int... extras){
			if(array == null) return extras;
			int[] result = new int[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static int[] remove(int[] array, int index){
			int[] result = new int[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static int[] reverseArray(int... array){
			int[] result = new int[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static long[] mergeArrays(long[] array, long... extras){
			if(array == null) return extras;
			long[] result = new long[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static long[] remove(long[] array, int index){
			long[] result = new long[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static long[] reverseArray(long... array){
			long[] result = new long[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static short[] mergeArrays(short[] array, short... extras){
			if(array == null) return extras;
			short[] result = new short[array.length + extras.length];
			System.arraycopy(array, 0, result, 0, array.length);
			System.arraycopy(extras, 0, result, array.length, extras.length);
			return result;
		}
		
		public static short[] remove(short[] array, int index){
			short[] result = new short[array.length - 1];
			System.arraycopy(array, 0, result, 0, index);
			System.arraycopy(array, index + 1, result, index, result.length - index);
			return result;
		}
		
		public static short[] reverseArray(short... array){
			short[] result = new short[array.length];
			for(int i = 0; i < array.length; i++) result[i] = array[array.length - 1 - i];
			return result;
		}
		
		public static List<Byte> sortedValues(byte... values){
			List<Byte> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Byte.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
		public static List<Character> sortedValues(char... values){
			List<Character> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Character.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
		public static List<Double> sortedValues(double... values){
			List<Double> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Double.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
		public static List<Float> sortedValues(float... values){
			List<Float> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Float.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
		public static List<Integer> sortedValues(int... values){
			List<Integer> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Integer.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
		public static List<Long> sortedValues(long... values){
			List<Long> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Long.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
		public static List<Short> sortedValues(short... values){
			List<Short> result = ContentBuilder.newArrayList();
			for(int i = 0; i < values.length; i++) result.add(Short.valueOf(values[i]));
			Collections.sort(result);
			return result;
		}
		
	}
	
}
