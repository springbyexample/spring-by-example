package com.spring.by.example.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Employee {
	
	private Long id;

	private String lastName;
	private String firstName;
	private Position position;	
	private Role role;
	private Field field;
	private Technology technology;

	@SuppressWarnings(value= "unchecked")
	private Set<Employee> buddySet = Collections.EMPTY_SET;

	public Employee() {

	}

	public Employee(String lastName, String firstName, Position potision,
			Role role, Field field, Technology technology) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.position = potision;
		this.role = role;
		this.field = field;
		this.technology = technology;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public Set<Employee> getBuddySet() {
		return Collections.unmodifiableSet(buddySet);
	}
	public void setBuddySet(Set<Employee> buddySet) {
		this.buddySet = buddySet;
	}
	public boolean addBuddy(Employee buddy) {
		if (buddySet.equals(Collections.EMPTY_SET)) {
			buddySet = new HashSet<Employee>();
		}
		return buddySet.add(buddy);
	}
	public boolean removeBuddy(Employee buddy) {
		if (buddySet.equals(Collections.EMPTY_SET)) {
			return false;
		}
		return buddySet.remove(buddy);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	

}
