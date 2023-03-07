package com.sulvic.io;

import static java.nio.ByteOrder.*;

public enum Endian{
	
	BIG,
	LITTLE;
	
	public static Endian getNative(){ return nativeOrder().equals(BIG_ENDIAN)? BIG: LITTLE; }
	
}
