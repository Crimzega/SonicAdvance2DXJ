package com.sulvic.io;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import com.sulvic.util.SwapHelper;

@SuppressWarnings({"unused"})
public class EndianInputStream extends InputStream implements DataInput{
	
	private static final Endian NATIVE = Endian.getNative();
	private final Endian endianness;
	private InputStream theStream;
	private long currPos;
	
	public EndianInputStream(InputStream stream){ this(stream, NATIVE); }
	
	public EndianInputStream(InputStream stream, Endian endian){
		theStream = stream;
		endianness = endian;
	}
	
	private static byte[] readPrimitive(EndianInputStream stream, Endian endian, int length) throws IOException{
		byte[] buffer = new byte[length];
		stream.read(buffer);
		switch(endian){
			case BIG:
				buffer = SwapHelper.swapBytes(buffer);
			break;
			default:
			break;
		}
		return buffer;
	}
	
	public boolean readBoolean() throws IOException{ return readByte() == 1; }
	
	public byte readByte() throws IOException{ return (byte)read(); }
	
	public char readChar() throws IOException{ return (char)((read() << 8) | read()); }
	
	public double readDouble() throws IOException{ return readDouble(endianness); }
	
	public double readDouble(Endian endian) throws IOException{ return BytesHelper.getDouble(readPrimitive(this, endian, 8)); }
	
	public float readFloat() throws IOException{ return readFloat(endianness); }
	
	public float readFloat(Endian endian) throws IOException{ return BytesHelper.getFloat(readPrimitive(this, endian, 4)); }
	
	public int available() throws IOException{ return theStream.available(); }
	
	public int read() throws IOException{
		int result = theStream.read();
		currPos++;
		return result;
	}
	
	public int read(byte[] buffer) throws IOException{ return read(buffer, 0, buffer.length); }
	
	public int read(byte[] buffer, int offset, int length) throws IOException{
		currPos += length;
		return theStream.read(buffer, offset, length);
	}
	
	public int readInt() throws IOException{ return readInt(endianness); }
	
	public int readInt(Endian endian) throws IOException{ return BytesHelper.getInt(readPrimitive(this, endian, 4)); }
	
	public int readUnsignedByte() throws IOException{ return readByte() & 0xFF; }
	
	public int readUnsignedShort() throws IOException{ return readUnsignedShort(endianness); }
	
	public int readUnsignedShort(Endian endian) throws IOException{ return readShort(endian) & 0xFFFF; }
	
	public int skipBytes(int count) throws IOException{
		int curr = 0, total = 0;
		while(total < count && (curr = (int)skip((long)(count - total))) > 0) total += curr;
		return 0;
	}
	
	public long readLong() throws IOException{ return readLong(endianness); }
	
	public long readLong(Endian endian) throws IOException{ return BytesHelper.getLong(readPrimitive(this, endian, 8)); }
	
	public long skip(long count) throws IOException{
		currPos += count;
		return theStream.skip(count);
	}
	
	public short readShort() throws IOException{ return readShort(endianness); }
	
	public short readShort(Endian endian) throws IOException{ return BytesHelper.getShort(readPrimitive(this, endian, 2)); }
	
	/**
	@deprecated This method does not properly convert bytes to characters.
	As of JSK 1.1, the preferred way to read lines of text is via the
	<code>BufferdReeader.readLine()</code> method. Programs that use the
	<code>EndianInputStream</code> class to read line can be converted to
	use the <code>BufferedReader</code> class by replacing code of the form:
	<blockquote><pre>
	EndianInputStream e = new EndianInputStream(in);
	</pre></blockquote>
	with:
	<blockquote><pre>
	BufferedReader e
		= new BufferedReader(new InputStreamReader(in));
	</pre></blockquote>
	@return the next line of text from this input stream.
	@exception IOException if an I/O error occurs.
	@see java.io.BufferedReader#readLine()
	*/
	@Deprecated
	public String readLine() throws IOException{
		StringBuilder builder = new StringBuilder();
		while(true){
			int checker, value;
			switch(value = read()){
				case -1:
					continue;
				case '\n':
					return builder.toString();
				case '\r':
					checker = read();
					if(checker == '\n' || checker == -1){
						if(!(theStream instanceof PushbackInputStream)) theStream = new PushbackInputStream(theStream);
						((PushbackInputStream)theStream).unread(checker);
					}
				break;
			}
			builder.append(value);
		}
	}
	
	public String readUTF() throws IOException{ return UTFHelper.readUTF(this); }
	
	public void readFully(byte[] buffer) throws IOException{ readFully(buffer, 0, buffer.length); }
	
	public void readFully(byte[] buffer, int offset, int length) throws IOException{
		if(length < 0) throw new IndexOutOfBoundsException();
		int num = 0;
		while(num < length){
			int count = read(buffer, offset + num, length + num);
			if(count < 0) throw new EOFException();
			num += count;
		}
	}
	
	public void close() throws IOException{ theStream.close(); }
	
}
