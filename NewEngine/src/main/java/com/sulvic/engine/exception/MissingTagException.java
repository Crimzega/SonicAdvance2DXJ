package com.sulvic.engine.exception;

public class MissingTagException extends RuntimeException{
	
	private static final long serialVersionUID = 1195742001106003702L;
	
	public MissingTagException(){ super("Missing the type for the List Tag"); }
	
}
