package com.example.listadapterexample;

public class Model {

	private final String _name;
	private final long _number;

	public Model(String name, long number){
		_name = name;
		_number = number;
	}
	
	public String getName(){
		return _name;
	}
	
	public long getNumber(){
		return _number;
	}	
	
}
