package com.edm.controller;

import java.util.List;

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


@Controller
public class DepartmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentService deptService;
	
	@GetMapping("/deptlist")
	public String deptList(Model model) {
		List<Department> listDept = deptService.getAllDepartments();
		model.addAttribute("listdept", listDept);
		logger.info("Department List");
		return "departmentlist";
	}
	
	@GetMapping("/department")
	public String showDepartmentForm(Model model) {
		model.addAttribute("department", new Department());	
		logger.info("Register Department");
		return "department";
	}
	
	@PostMapping("/create_dept")
	public String createDepartment(Department dept) {
		
		deptService.saveDepartment(dept);
		
		logger.info("created department : {}", dept.getDeptName());
		
		return "redirect:/deptlist";
	}
	
	@GetMapping("/edit-department/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Department dept = deptService.getById(id)
	      .orElseThrow(() -> new ItemNotFoundException(id));
	    logger.info("Update department : {}", dept.getDeptName());
	    model.addAttribute("department", dept);
	    return "update-dept";
	}
	
	@PostMapping("/update-department/{id}")
	public String updateDepartment(@PathVariable("id") long id, Department dept, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	    	dept.setId(id);
	    	logger.error("something went wrong");  
	        return "update-dept";
	    }
	    logger.info("Register department : {}", dept.getDeptName());    
	    deptService.saveDepartment(dept);
	    return "redirect:/deptlist";
	}
	    
	@GetMapping("/delete-department/{id}")
	public String deleteDepartment(@PathVariable("id") long id, Model model) {
	    Department dept = deptService.getById(id)
	      .orElseThrow(() -> new ItemNotFoundException(id));
	    logger.warn("Delete department : {}", dept.getDeptName());    
	    deptService.deleteDepartment(dept);
	    return "redirect:/deptlist";
	}
}
