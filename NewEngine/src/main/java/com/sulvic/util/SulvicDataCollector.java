package com.sulvic.util;

import java.lang.reflect.*;

@SuppressWarnings({"unchecked"})
public class SulvicDataCollector{
	
	private static Field getDeclaredField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{
		Class<?> cls = obj.getClass();
		Field field = cls.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
	
	private static Field getDeclaredStaticField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		Field field = cls.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
	
	private static Field getField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{
		Class<?> cls = obj.getClass();
		Field field = cls.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
	
	private static Field getStaticField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		Field field = cls.getField(name);
		field.setAccessible(true);
		return field;
	}
	
	private static Method getDeclaredMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException{
		Class<?> cls = obj.getClass();
		Class<?>[] paramClasses = new Class<?>[params.length];
		for(int i = 0; i < params.length; i++) paramClasses[i] = params[i].getClass();
		Method method = cls.getDeclaredMethod(name, paramClasses);
		method.setAccessible(true);
		return method;
	}
	
	private static Method getDeclaredStaticMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException{
		Class<?>[] paramClasses = new Class<?>[params.length];
		for(int i = 0; i < params.length; i++) paramClasses[i] = params[i].getClass();
		Method method = cls.getDeclaredMethod(name, paramClasses);
		method.setAccessible(true);
		return method;
	}
	
	private static Method getStaticMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException{
		Class<?>[] paramClasses = new Class<?>[params.length];
		for(int i = 0; i < params.length; i++) paramClasses[i] = params[i].getClass();
		Method method = cls.getMethod(name, paramClasses);
		method.setAccessible(true);
		return method;
	}
	
	private static Method getMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException{
		Class<?> cls = obj.getClass();
		Class<?>[] paramClasses = new Class<?>[params.length];
		for(int i = 0; i < params.length; i++) paramClasses[i] = params[i].getClass();
		Method method = cls.getMethod(name, paramClasses);
		method.setAccessible(true);
		return method;
	}
	
	public static <T> T getDeclaredGenericField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return (T)getDeclaredObjectField(obj, name); }
	
	public static <T> T getDeclaredGenericMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return (T)getDeclaredObjectMethod(obj, name, params);
	}
	
	public static <T> T getDeclaredStaticGenericField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return (T)getDeclaredStaticObjectField(cls, name); }
	
	public static <T> T getDeclaredStaticGenericMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return (T)getDeclaredStaticObjectMethod(cls, name, params);
	}
	
	public static <T> T getGenericField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return (T)getObjectField(obj, name); }
	
	public static <T> T getGenericMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return (T)getObjectMethod(obj, name, params);
	}
	
	public static <T> T getStaticGenericField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return (T)getStaticObjectField(cls, name); }
	
	public static <T> T getStaticGenericMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return (T)getStaticObjectMethod(cls, name, params);
	}
	
	public static boolean getBooleanField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getBoolean(obj); }
	
	public static boolean getBooleanMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Boolean.parseBoolean(getStringMethod(obj, name, params));
	}
	
	public static boolean getDeclaredBooleanField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getBoolean(obj); }
	
	public static boolean getDeclaredBooleanMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Boolean.parseBoolean(getDeclaredStringMethod(obj, name, params));
	}
	
	public static boolean getDeclaredStaticBooleanField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		return getDeclaredStaticField(cls, name).getBoolean(cls);
	}
	
	public static boolean getDeclaredStaticBooleanMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Boolean.parseBoolean(getDeclaredStaticStringMethod(cls, name, params));
	}
	
	public static boolean getStaticBooleanField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getBoolean(cls); }
	
	public static boolean getStaticBooleanMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Boolean.parseBoolean(getStaticStringMethod(cls, name, params));
	}
	
	public static byte getByteField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getByte(obj); }
	
	public static byte getByteMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Byte.parseByte(getStringMethod(obj, name, params));
	}
	
	public static byte getDeclaredByteField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getByte(obj); }
	
	public static byte getDeclaredByteMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Byte.parseByte(getDeclaredStringMethod(obj, name, params));
	}
	
	public static byte getDeclaredByteField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredStaticField(cls, name).getByte(cls); }
	
	public static byte getDeclaredByteMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Byte.parseByte(getDeclaredStaticStringMethod(cls, name, params));
	}
	
	public static byte getStaticByteField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getByte(cls); }
	
	public static byte getStaticByteMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Byte.parseByte(getStaticStringMethod(cls, name, params));
	}
	
	public static char getCharField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getChar(obj); }
	
	public static char getCharMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getStringMethod(obj, name, params).charAt(0);
	}
	
	public static char getDeclaredCharField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getChar(obj); }
	
	public static char getDeclaredCharMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getDeclaredStringMethod(obj, name, params).charAt(0);
	}
	
	public static char getDeclaredCharField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredStaticField(cls, name).getChar(cls); }
	
	public static char getDeclaredCharMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getDeclaredStaticStringMethod(cls, name, params).charAt(0);
	}
	
	public static char getStaticCharField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getChar(cls); }
	
	public static char getStaticCharMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getStaticStringMethod(cls, name, params).charAt(0);
	}
	
	public static double getDeclaredDoubleField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getDouble(obj); }
	
	public static double getDeclaredDoubleMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Double.parseDouble(getDeclaredStringMethod(obj, name, params));
	}
	
	public static double getDeclaredStaticDoubleField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		return getDeclaredStaticField(cls, name).getDouble(cls);
	}
	
	public static double getDeclaredStaticDoubleMethod(Class<?> obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Double.parseDouble(getDeclaredStaticStringMethod(obj, name, params));
	}
	
	public static double getDoubleField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getDouble(obj); }
	
	public static double getDoubleMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Double.parseDouble(getStringMethod(obj, name, params));
	}
	
	public static double getStaticDoubleField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getDouble(cls); }
	
	public static double getStaticDoubleMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Double.parseDouble(getStaticStringMethod(cls, name, params));
	}
	
	public static float getDeclaredFloatField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getFloat(obj); }
	
	public static float getDeclaredFloatMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Float.parseFloat(getDeclaredStringMethod(obj, name, params));
	}
	
	public static float getDeclaredStaticFloatField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		return getDeclaredStaticField(cls, name).getFloat(cls);
	}
	
	public static float getDeclaredStaticFloatMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Float.parseFloat(getDeclaredStaticStringMethod(cls, name, params));
	}
	
	public static float getFloatField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getFloat(obj); }
	
	public static float getFloatMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Float.parseFloat(getStringMethod(obj, name, params));
	}
	
	public static float getStaticFloatField(Class<?> obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(obj, name).getFloat(obj); }
	
	public static float getStaticFloatMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Float.parseFloat(getStaticStringMethod(cls, name, params));
	}
	
	public static int getDeclaredIntField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getInt(obj); }
	
	public static int getDeclaredIntMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Integer.parseInt(getDeclaredStringMethod(obj, name, params));
	}
	
	public static int getDeclaredIntField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredStaticField(cls, name).getInt(cls); }
	
	public static int getDeclaredIntMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Integer.parseInt(getDeclaredStaticStringMethod(cls, name, params));
	}
	
	public static int getIntField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getInt(obj); }
	
	public static int getIntMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Integer.parseInt(getStringMethod(obj, name, params));
	}
	
	public static int getStaticIntField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getInt(cls); }
	
	public static int getStaticIntMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Integer.parseInt(getStaticStringMethod(cls, name, params));
	}
	
	public static long getDeclaredLongField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getLong(obj); }
	
	public static long getDeclaredLMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Long.parseLong(getDeclaredStringMethod(obj, name, params));
	}
	
	public static long getDeclaredStaticLongField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredStaticField(cls, name).getLong(cls); }
	
	public static long getDeclaredStaticLMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Long.parseLong(getDeclaredStaticStringMethod(cls, name, params));
	}
	
	public static long getLongField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getLong(obj); }
	
	public static long getLongMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Long.parseLong(getStringMethod(obj, name, params));
	}
	
	public static long getStaticLongField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getLong(cls); }
	
	public static long getStaticLongMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Long.parseLong(getStaticStringMethod(cls, name, params));
	}
	
	public static Object getDeclaredObjectField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).get(obj); }
	
	public static Object getDeclaredObjectMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getDeclaredMethod(obj, name, params).invoke(obj, params);
	}
	
	public static Object getDeclaredStaticObjectField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredStaticField(cls, name).get(cls); }
	
	public static Object getDeclaredStaticObjectMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getDeclaredStaticMethod(cls, name, params).invoke(cls, params);
	}
	
	public static Object getObjectField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).get(obj); }
	
	public static Object getObjectMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getMethod(obj, name, params).invoke(obj, params);
	}
	
	public static Object getStaticObjectField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).get(cls); }
	
	public static Object getStaticObjectMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getStaticMethod(cls, name, params).invoke(cls, params);
	}
	
	public static short getDeclaredShortField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredField(obj, name).getShort(obj); }
	
	public static short getDeclaredShortMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Short.parseShort(getDeclaredStringMethod(obj, name, params));
	}
	
	public static short getDeclaredStaticShortField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		return getDeclaredStaticField(cls, name).getShort(cls);
	}
	
	public static short getDeclaredShortMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Short.parseShort(getDeclaredStaticStringMethod(cls, name, params));
	}
	
	public static short getStaticShortField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticField(cls, name).getShort(cls); }
	
	public static short getStaticShortMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Short.parseShort(getStaticStringMethod(cls, name, params));
	}
	
	public static short getShortField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getField(obj, name).getShort(obj); }
	
	public static short getShortMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return Short.parseShort(getStringMethod(obj, name, params));
	}
	
	public static String getDeclaredStringField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getDeclaredObjectField(obj, name).toString(); }
	
	public static String getDeclaredStringMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getDeclaredObjectMethod(obj, name, params).toString();
	}
	
	public static String getDeclaredStaticStringField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{
		return getDeclaredStaticObjectField(cls, name).toString();
	}
	
	public static String getDeclaredStaticStringMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getDeclaredStaticObjectMethod(cls, name, params).toString();
	}
	
	public static String getStaticStringField(Class<?> cls, String name) throws NoSuchFieldException, IllegalAccessException{ return getStaticObjectField(cls, name).toString(); }
	
	public static String getStaticStringMethod(Class<?> cls, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getStaticObjectMethod(cls, name, params).toString();
	}
	
	public static String getStringField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException{ return getObjectField(obj, name).toString(); }
	
	public static String getStringMethod(Object obj, String name, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		return getObjectMethod(obj, name, params).toString();
	}
	
}
