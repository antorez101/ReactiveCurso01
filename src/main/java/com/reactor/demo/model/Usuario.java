package com.reactor.demo.model;

public class Usuario {
	
	public String name;
	public String lastName;
	
	
	public Usuario(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Usuario [name=" + name + ", lastName=" + lastName + "]";
	}
	
	
	

}
