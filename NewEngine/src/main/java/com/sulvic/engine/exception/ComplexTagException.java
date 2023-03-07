package com.sulvic.engine.exception;

public class ComplexTagException extends RuntimeException{
	
	private static final long serialVersionUID = 8470781305667007465L;
	
	public ComplexTagException(){ super("Tried to read a complex tag: Depth > 512"); }
	
}
