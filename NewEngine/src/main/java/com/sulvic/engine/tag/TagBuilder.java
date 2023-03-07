package com.sulvic.engine.tag;

import java.util.Collections;
import java.util.List;

import com.sulvic.util.ContentBuilder;

public class TagBuilder{
	
	private static String byteArrayFormat(DataTagByteArray tagArray){
		byte[] values = tagArray.getValues();
		StringBuilder builder = new StringBuilder("[B:");
		for(int i = 0; i < values.length; i++){
			if(i != 0) builder.append(',');
			builder.append(values[i]).append('B');
		}
		return builder.append(']').toString();
	}
	
	private static String byteFormat(DataTagByte tagByte){ return String.format("%db", tagByte.getAsNumber()); }
	
	private static String doubleArrayFormat(DataTagDoubleArray tagArray){
		double[] values = tagArray.getValues();
		StringBuilder builder = new StringBuilder("[D:");
		for(int i = 0; i < values.length; i++){
			if(i != 0) builder.append(',');
			builder.append(values[i]).append('D');
		}
		return builder.append(']').toString();
	}
	
	private static String doubleFormat(DataTagDouble tagDouble){ return String.format("%fd", tagDouble.getAsNumber()); }
	
	private static String floatArrayFormat(DataTagFloatArray tagArray){
		float[] values = tagArray.getValues();
		StringBuilder builder = new StringBuilder("[F:");
		for(int i = 0; i < values.length; i++){
			if(i != 0) builder.append(',');
			builder.append(values[i]).append('F');
		}
		return builder.append(']').toString();
	}
	
	private static String floatFormat(DataTagFloat tagFloat){ return String.format("%ff", tagFloat.getAsNumber()); }
	
	private static String intArrayFormat(DataTagIntArray tagArray){
		int[] values = tagArray.getValues();
		StringBuilder builder = new StringBuilder("[I:");
		for(int i = 0; i < values.length; i++){
			if(i != 0) builder.append(',');
			builder.append(values[i]).append('I');
		}
		return builder.append(']').toString();
	}
	
	private static String intFormat(DataTagInt tagInt){ return String.format("%d", tagInt.getAsNumber()); }
	
	private static String longArrayFormat(DataTagLongArray tagArray){
		long[] values = tagArray.getValues();
		StringBuilder builder = new StringBuilder("[L:");
		for(int i = 0; i < values.length; i++){
			if(i != 0) builder.append(',');
			builder.append(values[i]).append('L');
		}
		return builder.append(']').toString();
	}
	
	private static String listFormat(DataTagList tagList){
		StringBuilder builder = new StringBuilder('[');
		for(int i = 0; i < tagList.size(); i++){
			if(i != 0) builder.append(',');
			builder.append(tagString(tagList.get(i)));
		}
		return builder.append(']').toString();
	}
	
	private static String longFormat(DataTagLong tagLong){ return String.format("%dL", tagLong.getAsNumber()); }
	
	private static String mapFormat(DataTagCompound tagCompound){
		StringBuilder builder = new StringBuilder('{');
		List<String> keys = ContentBuilder.newArrayList();
		keys.addAll(tagCompound.keySet());
		Collections.sort(keys);
		for(String name: keys){
			if(builder.length() != 1) builder.append(',');
			builder.append(stringFormat(name)).append(':').append(tagString(tagCompound.getTag(name)));
		}
		return builder.append('}').toString();
	}
	
	private static String shortArrayFormat(DataTagShortArray tagArray){
		short[] values = tagArray.getValues();
		StringBuilder builder = new StringBuilder("[S:");
		for(int i = 0; i < values.length; i++){
			if(i != 0) builder.append(',');
			builder.append(values[i]).append('S');
		}
		return builder.append(']').toString();
	}
	
	private static String shortFormat(DataTagShort tagShort){ return String.format("%ds", tagShort.getAsNumber()); }
	
	private static String stringFormat(DataTagString tagString){ return stringFormat(tagString.getString()); }
	
	public static String tagString(IDataTag tag){
		switch(tag.getId()){
			case 0x0: return "END";
			case 0x1: return byteFormat((DataTagByte)tag);
			case 0x2: return shortFormat((DataTagShort)tag);
			case 0x3: return intFormat((DataTagInt)tag);
			case 0x4: return longFormat((DataTagLong)tag);
			case 0x5: return floatFormat((DataTagFloat)tag);
			case 0x6: return doubleFormat((DataTagDouble)tag);
			case 0x7: return byteArrayFormat((DataTagByteArray)tag);
			case 0x8: return shortArrayFormat((DataTagShortArray)tag);
			case 0x9: return intArrayFormat((DataTagIntArray)tag);
			case 0xA: return longArrayFormat((DataTagLongArray)tag);
			case 0xB: return floatArrayFormat((DataTagFloatArray)tag);
			case 0xC: return doubleArrayFormat((DataTagDoubleArray)tag);
			case 0xD: return stringFormat((DataTagString)tag); 
			case 0xE: return listFormat((DataTagList)tag);
			case 0xF: return mapFormat((DataTagCompound)tag);
			default: return "";
		}
	}
	
	public static String stringFormat(String str){
		StringBuilder builder = new StringBuilder(' ');
		char startQuote = (char)0;
		for(int i = 0; i < str.length(); i++){
			char ch = str.charAt(i);
			if(ch == '\\') builder.append('\\');
			else if(ch == '"' || ch == '\''){
				if(startQuote == (char)0) startQuote = ch == '"'? '\'': '"';
				if(startQuote == ch) builder.append('\\');
			}
			builder.append(ch);
		}
		if(startQuote == (char)0) startQuote = '"';
		builder.setCharAt(0, startQuote);
		return builder.append(startQuote).toString();
	}
	
}
