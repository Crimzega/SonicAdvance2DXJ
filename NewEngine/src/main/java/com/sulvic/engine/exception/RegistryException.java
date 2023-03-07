package com.sulvic.engine.exception;

public class RegistryException extends RuntimeException{
	
	private static final long serialVersionUID = 3755726042314246068L;
	
	public RegistryException(String name){ super("An object already exists with the giben name. Name: " + name); }
	
}
