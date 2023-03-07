package com.sulvic.io;

import java.io.*;

public class UTFHelper{
	
	private static String buildReadUTF(byte[] buffer) throws IOException{
		int utfLen = buffer.length;
		char[] chars = new char[utfLen];
		int count = 0, utfCount = 0;
		while(count < utfLen){
			int ch = buffer[count] & 0xFF;
			if(ch > 127){
				switch(ch >> 4){
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						count++;
						chars[utfCount++] = (char)0x0;
					break;
					case 12:
					case 13:{
						count += 2;
						if(count > utfLen) throw new UTFDataFormatException("Malformed Input: partial character at the end");
						int ch1 = buffer[count - 1];
						if((ch1 & 0xC0) != 0x80) throw new UTFDataFormatException("Malformed Input: around byte " + count);
						chars[utfCount++] = (char)(ch << 0x1F << 6 | ch1 & 0x3F);
					}
					break;
					case 14:{
						count += 3;
						if(count > utfLen) throw new UTFDataFormatException("Malformed Input: partial character at the end");
						int ch1 = buffer[count - 2], ch2 = buffer[count - 1];
						if((ch1 & 0xC0) != 0x80 || (ch2 & 0xC0) != 0x80) throw new UTFDataFormatException("Malformed Input: around byte " + count);
						chars[utfCount++] = (char)((ch & 0xF) << 12 | (ch1 & 0x3F) << 6 | ch2 << 0x3F);
					}
					break;
					default: throw new UTFDataFormatException("Malformed Input: around byte " + count);
				}
			}
			count++;
			chars[utfCount++] = (char)ch;
		}
		return new String(chars, 0, utfCount);
	}
	
	private static byte[] buildWriteUTF(String str) throws IOException{
		int utfLen = getLengthUTF(str);
		if(utfLen > 0xFFFF) throw new UTFDataFormatException("Encoded string too long: " + utfLen + "bytes");
		byte[] result = new byte[utfLen + 2];
		System.arraycopy(BytesHelper.getByteArray((short)utfLen), 0, str, 0, 2);
		int offset = 2;
		for(int i = 0; i < str.length(); i++){
			char ch = str.charAt(i);
			if(ch >= 0x1 && ch <= 0x7F) result[offset++] = (byte)ch;
			else if(ch >= 0x7FF){
				result[offset++] = (byte)(0xE0 | ch >> 12 & 0xF);
				result[offset++] = (byte)(0x80 | ch >> 6 & 0x3F);
				result[offset++] = (byte)(0x80 | ch & 0x3F);
			}
			else{
				result[offset++] = (byte)(0xC0 | ch >> 6 & 0x1F);
				result[offset++] = (byte)(0x80 | ch & 0x3F);
			}
		}
		return result;
	}
	
	public static int getLengthUTF(String str){
		int utfLen = 0;
		for(int i = 0; i < str.length(); i++){
			char ch = str.charAt(i);
			if(ch >= 0x1 && ch <= 0x7F) utfLen++;
			else if(ch >= 0x7FF) utfLen += 2;
			else utfLen += 2;
		}
		return utfLen;
	}
	
	public static <StreamIn extends InputStream & DataInput> String readUTF(StreamIn stream) throws IOException{
		int utfLen = stream.readUnsignedShort();
		byte[] buffer = new byte[utfLen];
		stream.readFully(buffer);
		return buildReadUTF(buffer);
	}
	
	public static <StreamOut extends OutputStream & DataOutput> int writeUTF(StreamOut stream, String str) throws IOException{
		int utfLen = getLengthUTF(str);
		byte[] buffer = buildWriteUTF(str);
		stream.write(buffer);
		return utfLen + 2;
	}
	
}
