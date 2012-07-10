package ua.com.springbyexample.domain;

public enum Field {
	
	QA("Quality Assurance"),
	WAD("Web Application Developement"),
	EMBEDDED("Embedded developement"),
	MOBILE("Mobile Platforms Development");
	
	private final String name;
	
	Field(String name) {
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
