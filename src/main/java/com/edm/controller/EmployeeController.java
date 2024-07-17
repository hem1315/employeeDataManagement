package com.edm.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.edm.exception.ItemNotFoundException;
import com.edm.model.Department;
import com.edm.service.DepartmentService;
import com.edm.service.EmployeeService;
import com.edm.util.Employee;


@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/employees")
	public String showAllEmployees(Model model) {
		List<com.edm.model.Employee> employeeList = employeeService.getAllEmployees();
		model.addAttribute("empList", employeeList);
		return "employees";
	}
	
	@GetMapping("/employee")
	public String addEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		List<Department> departmentsList = departmentService.getAllDepartments();
		logger.info("Employee List");
		model.addAttribute("depts",departmentsList);
		return "employee";
	}
	
	
	@PostMapping("/process_employee")
	public String processEmployee(Employee employee) {
		com.edm.model.Employee emp = new com.edm.model.Employee(employee);
		if(employee.getId() != null) {
			emp.setId(employee.getId());
		}else {
			logger.error("Employee Id not found");
		}
		if(employee.getDeptId() != null) {
			Optional<Department> dept = departmentService.getById(employee.getDeptId());
			emp.setDepartment(dept.get());
		}else {
			logger.error("Department Id not found");
		}
		logger.info("Register employee : {}", employee.getFirstName());
		
		employeeService.saveEmployee(emp);
		
		return "redirect:/employees";
	}


	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		com.edm.model.Employee emp = employeeService.getById(id)
				.orElseThrow(() -> new ItemNotFoundException(id));
		logger.info("Update employee : {}", emp.getFirstName());
		model.addAttribute("employee", emp);
		List<Department> departmentsList = departmentService.getAllDepartments();
		model.addAttribute("depts", departmentsList);
		return "update-emp"; // Return the view name for the update form
	}
	
	@PostMapping("/update/{id}")
	public String updateEmployee(@PathVariable("id") long id, com.edm.model.Employee emp,
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        emp.setId(id);
	        logger.error("something went wrong");    
	        return "update-emp";
	    }
	    logger.info("Update employee : {}", emp.getFirstName());        
	    employeeService.saveEmployee(emp);
	    return "redirect:/employees";
	}
	    
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") long id, Model model) {
	    com.edm.model.Employee emp = employeeService.getById(id)
	      .orElseThrow(() -> new ItemNotFoundException(id));
	    logger.warn("Delete employee : {}", emp.getFirstName()); 
	    employeeService.deleteEmployee(emp);
	    return "redirect:/employees";
	}
}
