package com.sulvic.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

import com.sulvic.util.SwapHelper;

@SuppressWarnings({"unused"})
public class EndianOutputStream extends OutputStream implements DataOutput{
	
	private static final Endian NATIVE = Endian.getNative();
	private final Endian endianness;
	private final OutputStream theStream;
	private long currPos;
	
	public EndianOutputStream(OutputStream stream){ this(stream, NATIVE); }
	
	public EndianOutputStream(OutputStream stream, Endian endian){
		theStream = stream;
		endianness = endian;
	}
	
	private static void writePrimitive(EndianOutputStream stream, Endian endian, byte[] buffer) throws IOException{
		switch(endian){
			case LITTLE:
				buffer = SwapHelper.swapBytes(buffer);
			break;
			default:
			break;
		}
		stream.write(buffer);
	}
	
	public void close() throws IOException{ theStream.close(); }
	
	public void flush() throws IOException{ theStream.flush(); }
	
	public void write(int value) throws IOException{
		theStream.write(value);
		currPos++;
	}
	
	public void write(byte[] buffer) throws IOException{ super.write(buffer, 0, buffer.length); }
	
	public void write(byte[] buffer, int offset, int length) throws IOException{
		theStream.write(buffer, offset, length);
		currPos += length;
	}
	
	public void writeBoolean(boolean value) throws IOException{ writeByte((byte)(value? 1: 0)); }
	
	public void writeByte(int value) throws IOException{ write(value); }
	
	public void writeBytes(String str) throws IOException{ for(char ch: str.toCharArray()) writeByte((byte)ch); }
	
	public void writeDouble(Endian endian, double value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeDouble(double value) throws IOException{ writeDouble(endianness, value); }
	
	public void writeChar(int value) throws IOException{
		write((value >> 8) & 0xFF);
		write(value & 0xFF);
	}
	
	public void writeChars(String str) throws IOException{ for(char ch: str.toCharArray()) writeChar(ch); }
	
	public void writeFloat(Endian endian, float value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeFloat(float value) throws IOException{ writeFloat(endianness, value); }
	
	public void writeInt(Endian endian, int value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeInt(int value) throws IOException{ writeInt(endianness, value); }
	
	public void writeLong(Endian endian, long value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeLong(long value) throws IOException{ writeLong(endianness, value); }
	
	public void writeShort(Endian endian, int value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value & 0xFFFF)); }
	
	public void writeShort(int value) throws IOException{ writeShort(endianness, value); }
	
	public void writeUTF(String str) throws IOException{ UTFHelper.writeUTF(this, str); }
	
}
