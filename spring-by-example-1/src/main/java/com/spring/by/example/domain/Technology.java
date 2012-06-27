package com.spring.by.example.domain;

public enum Technology {
	
	JAVA("Java"),
	DOTNET(".Net"),
	PHP("PHP");
	
	private final String name;
	
	Technology(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {		
		return getName();
	}	

}
