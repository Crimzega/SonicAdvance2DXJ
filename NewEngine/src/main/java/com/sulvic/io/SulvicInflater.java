package com.sulvic.io;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.sulvic.util.SulvicMath;
import com.sulvic.util.SwapHelper;

public class SulvicInflater extends InputStream implements DataInput{
	
	private static final Endian NATIVE = Endian.getNative();
	private byte[] uncompressedBytes;
	private final Endian endianness;
	private final Inflater theInflater = new Inflater();
	private final InputStream theStream;
	private int theOffset = 0;
	
	public SulvicInflater(InputStream stream){ this(stream, NATIVE); }
	
	public SulvicInflater(InputStream stream, Endian endian){
		theStream = stream;
		endianness = endian;
		byte[] compressedLength = new byte[4];
		try{
			theStream.read(compressedLength);
			byte[] compressed = new byte[BytesHelper.getInt(compressedLength)];
			theStream.read(compressed);
			byte[] temp = new byte[0xFFFF];
			theInflater.setInput(compressed);
			int length = theInflater.inflate(temp);
			uncompressedBytes = new byte[length];
			System.arraycopy(temp, 0, temp, 0, length);
		}
		catch(IOException | DataFormatException ex){
			ex.printStackTrace();
		}
	}
	
	private void update(int count){ theOffset += count; }
	
	private static byte[] readPrimitive(SulvicInflater inflater, Endian endian, int size){
		byte[] result = new byte[size];
		System.arraycopy(inflater.uncompressedBytes, inflater.theOffset, result, 0, size);
		switch(endian){
			case LITTLE:
				result = SwapHelper.swapBytes(result);
			break;
			default:
			break;
		}
		inflater.update(size);
		return result;
	}
	
	public int available(){ return uncompressedBytes.length - theOffset; }
	
	public int skipBytes(int count) throws IOException{
		int skipped = SulvicMath.minInt(available(), count);
		update(count);
		return skipped;
	}
	
	public boolean readBoolean() throws IOException{ return readByte() == 1; }
	
	public byte readByte() throws IOException{
		byte result = uncompressedBytes[theOffset];
		update(1);
		return result;
	}
	
	public char readChar() throws IOException{ return (char)((read() << 8) + read()); }
	
	public double readDouble() throws IOException{ return readDouble(endianness); }
	
	public double readDouble(Endian endian) throws IOException{ return BytesHelper.getDouble(readPrimitive(this, endian, 8)); }
	
	public float readFloat() throws IOException{ return readFloat(endianness); }
	
	public float readFloat(Endian endian) throws IOException{ return BytesHelper.getFloat(readPrimitive(this, endian, 4)); }
	
	public int read() throws IOException{ return readByte(); }
	
	public int readInt() throws IOException{ return readInt(endianness); }
	
	public int readInt(Endian endian) throws IOException{ return BytesHelper.getInt(readPrimitive(this, endian, 4)); }
	
	public int readUnsignedByte() throws IOException{ return readByte() & 0xFF; }
	
	public int readUnsignedShort() throws IOException{ return readUnsignedShort(endianness); }
	
	public int readUnsignedShort(Endian endian) throws IOException{ return readShort(endian) & 0xFFFF; }
	
	public long readLong() throws IOException{ return readLong(endianness); }
	
	public long readLong(Endian endian) throws IOException{ return BytesHelper.getLong(readPrimitive(this, endian, 8)); }
	
	public short readShort() throws IOException{ return readShort(endianness); }
	
	public short readShort(Endian endian) throws IOException{ return BytesHelper.getShort(readPrimitive(this, endian, 2)); }
	
	public String readLine() throws IOException{
		StringBuffer stringBuff = new StringBuffer();
		boolean lineEnd = false;
		int value = -1;
		while(!lineEnd){
			switch(value = read()){
				case -1:
				case '\n':
					lineEnd = true;
				break;
				case '\r':
					lineEnd = true;
					if(read() != '\n') update(-1);
				break;
				default:
					stringBuff.append(value);
				break;
			}
		}
		if(value == -1 && stringBuff.length() == 0) return null;
		return stringBuff.toString();
	}
	
	public String readUTF() throws IOException{ return UTFHelper.readUTF(this); }
	
	public void close() throws IOException{ theStream.close(); }
	
	public void readFully(byte[] buffer) throws IOException{ readFully(buffer, 0, buffer.length); }
	
	public void readFully(byte[] buffer, int offset, int length) throws IOException{
		System.arraycopy(uncompressedBytes, offset, buffer, 0, length);
		update(offset);
	}
	
}
