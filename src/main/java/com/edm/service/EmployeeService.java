package com.edm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edm.model.Employee;
import com.edm.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	public Employee saveEmployee(Employee emp) {
		return employeeRepository.save(emp);
	}

	public Optional<Employee> getById(long id) {
		return employeeRepository.findById(id);
	}

	public void deleteEmployee(Employee emp) {
		employeeRepository.delete(emp);
	}

}
