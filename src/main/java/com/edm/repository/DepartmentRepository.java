package com.edm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edm.model.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
}
