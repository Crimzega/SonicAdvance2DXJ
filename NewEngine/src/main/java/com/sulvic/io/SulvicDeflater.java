package com.sulvic.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

import com.sulvic.util.SulvicCollections;
import com.sulvic.util.SwapHelper;

public class SulvicDeflater extends OutputStream implements DataOutput{
	
	private static final Endian NATIVE = Endian.getNative();
	private byte[] writableBytes;
	private final Deflater theDeflater;
	private final Endian endianness;
	private final OutputStream theStream;
	
	public SulvicDeflater(OutputStream stream){ this(stream, 9); }
	
	public SulvicDeflater(OutputStream stream, Endian endian){ this(stream, 9, endian); }
	
	public SulvicDeflater(OutputStream stream, int level){ this(stream, level, NATIVE); }
	
	public SulvicDeflater(OutputStream stream, int level, Endian endian){
		theStream = stream;
		theDeflater = new Deflater(level);
		endianness = endian;
	}
	
	private static void writePrimitive(SulvicDeflater deflater, Endian endian, byte[] buffer){
		switch(endian){
			case LITTLE:
				buffer = SwapHelper.swapBytes(buffer);
			break;
			default:
			break;
		}
		deflater.update(buffer);
	}
	
	private void update(byte... buffer){ writableBytes = SulvicCollections.Primitives.mergeArrays(writableBytes, buffer); }
	
	public void close() throws IOException{ theStream.close(); }
	
	public void flush() throws IOException{
		byte[] written = new byte[writableBytes.length];
		theDeflater.setInput(written);
		int length = theDeflater.deflate(written);
		byte[] buffer = BytesHelper.getByteArray(length);
		switch(endianness){
			case LITTLE:
				buffer = SwapHelper.swapBytes(buffer);
			break;
			default:
			break;
		}
		theStream.write(buffer);
		theStream.write(written, 0, length);
		theStream.flush();
	}
	
	public void write(byte[] buffer) throws IOException{ write(buffer, 0); }
	
	public void write(byte[] buffer, int offset) throws IOException{ write(buffer, offset, buffer.length - offset); }
	
	public void write(byte[] buffer, int offset, int length) throws IOException{ super.write(buffer, offset, length); }
	
	public void write(int value) throws IOException{ writeByte(value); }
	
	public void writeBoolean(boolean value) throws IOException{ update((byte)(value? 1: 0)); }
	
	public void writeByte(int value) throws IOException{ update((byte)(value & 0xFF)); }
	
	public void writeBytes(String str) throws IOException{ for(char ch: str.toCharArray()) writeByte((byte)ch); }
	
	public void writeChar(int ch) throws IOException{ update((byte)((ch >>> 8) & 0xFF), (byte)((ch >>> 0) & 0xFF)); }
	
	public void writeChars(String str) throws IOException{ for(char ch: str.toCharArray()) writeChar((int)ch); }
	
	public void writeDouble(Endian endian, double value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeDouble(double value) throws IOException{ writeDouble(endianness, value); }
	
	public void writeFloat(Endian endian, float value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeFloat(float value) throws IOException{ writeFloat(endianness, value); }
	
	public void writeInt(Endian endian, int value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeInt(int value) throws IOException{ writeInt(endianness, value); }
	
	public void writeLong(Endian endian, long value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray(value)); }
	
	public void writeLong(long value) throws IOException{ writeLong(endianness, value); }
	
	public void writeShort(Endian endian, int value) throws IOException{ writePrimitive(this, endian, BytesHelper.getByteArray((short)(value & 0xFFFF))); }
	
	public void writeShort(int value) throws IOException{ writeShort(endianness, value); }
	
	public void writeUTF(String str) throws IOException{ UTFHelper.writeUTF(this, str); }
	
}
