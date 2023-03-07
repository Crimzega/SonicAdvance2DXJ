package com.sulvic.engine.exception;

public class MismatchTagException extends RuntimeException{
	
	private static final long serialVersionUID = 5521838529375314360L;
	
	public MismatchTagException(String format, Object... args){ super(String.format(format, args)); }
	
}
