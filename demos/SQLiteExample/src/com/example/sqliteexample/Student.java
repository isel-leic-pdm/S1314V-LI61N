package com.example.sqliteexample;

public class Student {
	
	private final int _number;
	private String _name;
	
	public int getNumber(){return _number;}
	public String getName(){return _name;}
	
	public void changeNameTo(String name){
		_name = name;
	}
	
	public Student(int number, String name){
		_number = number;
		_name = name;
	}
}
