package ua.com.springbyexample.domain;

public enum Position {	
	
	ASSOCIATE("Associate"),
	MIDLEVEL("Midlevel"),
	SENIOR("Senior");
	
	private static final String SWE = "Software Engineer";
	
	private final String name;
	
	Position(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name + " " + SWE;
	}
	
	@Override
	public String toString() {		
		return getName();
	}

}
