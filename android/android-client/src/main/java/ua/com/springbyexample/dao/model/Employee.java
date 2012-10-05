package ua.com.springbyexample.dao.model;


public class Employee {

	private Long id;

	private String firstName;

	private String lastName;

	private String project;
	
	//TODO: add status field for sync status

	// private Set<Employee> projectMates;

	public Employee() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

//	public Set<Employee> getProjectMates() {
//		return projectMates;
//	}
//
//	public void setProjectMates(Set<Employee> projectMates) {
//		this.projectMates = projectMates;
//	}

}
