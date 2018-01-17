package com.logfam.activemq.ptp;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable{

	private int num;
	private String name;
	
	public Person(int num, String name) {
		this.num = num;
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "["+this.num+","+this.name+"]";
	}

	
}
