package com.sulvic.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

@SuppressWarnings({"unchecked"})
public class SulvicIO{
	
	protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
	
	public static BufferedReader getBufferedReader(File file) throws IOException{ return getBufferedReader(new FileReader(file)); }
	
	public static BufferedReader getBufferedReader(InputStream stream){ return getBufferedReader(new InputStreamReader(stream)); }
	
	public static BufferedReader getBufferedReader(Reader reader){ return reader instanceof BufferedReader? (BufferedReader)reader: new BufferedReader(reader); }
	
	public static BufferedReader getBufferedReader(String file) throws IOException{ return getBufferedReader(new File(file)); }
	
	public static BufferedWriter getBufferedWriter(File file) throws IOException{ return getBufferedWriter(new FileWriter(file)); }
	
	public static BufferedWriter getBufferedWriter(OutputStream stream){ return getBufferedWriter(new OutputStreamWriter(stream)); }
	
	public static BufferedWriter getBufferedWriter(Writer writer){ return writer instanceof BufferedWriter? (BufferedWriter)writer: new BufferedWriter(writer); }
	
	public static BufferedWriter getBufferedWriter(String file) throws IOException{ return getBufferedWriter(new File(file)); }
	
	public static Image getImage(String path){ return TOOLKIT.getImage(path); }
	
	public static Image getImageResource(String path){ return TOOLKIT.getImage(SulvicIO.class.getResource(path)); }
	
	public static InputStream getInputStream(String path){ return SulvicIO.class.getResourceAsStream(path); }
	
	public static <T extends Closeable> void closeQuietly(T... closeables) throws IOException{
		for(T closeable: closeables){
			if(closeable instanceof Flushable) ((Flushable)closeable).flush();
			closeable.close();
		}
	}
	
}
