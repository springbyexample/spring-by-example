package ua.com.springbyexample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name  = "employee")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;

    @Past
    private Date date;

	@NotNull(message = "firstName should not be null")
	@Length(min = 3,max = 30, message = "firstName should be longer than 3 characters and shorter than 30 characters")
	@Column(name = "first_name")
	private String firstName;
	

	@NotNull(message = "lastName should not be null")
	@Length(min = 3,max = 30, message = "lastName should be longer than 3 characters and shorter than 30 characters")
	@Column(name = "last_name")
	private String lastName;	
	
	@Length(max = 30, message = "project should not longer than 30 charactes")
	@Column(name = "project")
	private String project;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "project_mates", joinColumns = @JoinColumn(name = "employee1", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employee2", referencedColumnName = "id"))	
	@JsonIgnore
	@XmlTransient
	private Set<Employee> projectMates;

	
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

	public Set<Employee> getProjectMates() {
		return projectMates;
	}

	public void setProjectMates(Set<Employee> projectMates) {
		this.projectMates = projectMates;
	}	

}
