package ua.com.springbyexample.domain;

public enum Role {
	
	DEV("Developer"),
	TL("Team Lead"),
	CM("Customer Manager");
	
	private final String roleName;
	
	Role(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	@Override
	public String toString() {		
		return getRoleName();
	}

}
