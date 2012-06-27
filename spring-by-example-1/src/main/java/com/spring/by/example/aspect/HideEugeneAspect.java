package com.spring.by.example.aspect;

import java.util.Set;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.spring.by.example.domain.Employee;


@Component
@Aspect
public class HideEugeneAspect {
	
	private static final String EUGENE_LAST_NAME = "Scripnik";
	
	@AfterReturning(value = "execution(* com.spring.by.example.service.EmployeeService.*(..))", returning = "serviceResult")
	public Set<Employee> hideEugene(Set<Employee> serviceResult) {
		for (Employee employee : serviceResult) {
			if (employee.getLastName().equals(EUGENE_LAST_NAME)) {
				serviceResult.remove(employee);
			}
		}		
		return serviceResult;
	}	

}
