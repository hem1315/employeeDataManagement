package com.edm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.edm.model.Department;
import com.edm.repository.DepartmentRepository;
import com.edm.service.DepartmentService;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTests {

	@Mock
	private DepartmentRepository repo;

	@InjectMocks
	private DepartmentService service;

	@Test
	void testGetAllDepartments() {
		// Arrange
		List<Department> mockList = new ArrayList<>();
		Department department1 = new Department(1L, "HR");
		Department department2 = new Department(2L, "IT");
		mockList.add(department1);
		mockList.add(department2);

		// Act
		when(repo.findAll()).thenReturn(mockList);
		List<Department> deptList = service.getAllDepartments();

		// Assert
		assertThat(deptList).isNotNull();
		assertThat(deptList.size()).isEqualTo(2);
	}

	@Test
	void testGetDepartmentById() {
		// Arrange
		long id = 1L;
		Department department = new Department(id, "HR");

		// Act
		when(repo.findById(id)).thenReturn(Optional.of(department));
		Optional<Department> dept = service.getById(id);

		// Assert
		assertTrue(dept.isPresent());
		assertSame(dept.get(), department);
	}

	@Test
	void testGetDepartmentById_WhenDepartmentNotExist_ReturnsOptionalEmpty() {
		// Arrange
		long departmentId = 2L;

		// Act
		when(repo.findById(departmentId)).thenReturn(Optional.empty());
		Optional<Department> result = service.getById(departmentId);

		// Assert
		assertFalse(result.isPresent());
	}

	@Test
	void testCreateDepartment_ReturnsDepartment() {
		// Arrange
		Department dept = new Department();
		dept.setDeptName("IT");

		// Act
		when(repo.save(Mockito.any(Department.class))).thenReturn(dept);
		Department savedDepartment = service.saveDepartment(dept);

		// Assert
		Assertions.assertThat(savedDepartment).isNotNull();
	}

	@Test
	void testUpdateDepartment_ReturnsDepartment() {
		// Arrange
		long departmentId = 1L;
		Department dept = new Department();
		dept.setId(departmentId);
		dept.setDeptName("HR");

		// Act
		when(repo.save(dept)).thenReturn(dept);
		Department updatedDepartment = service.saveDepartment(dept);

		// Assert
		Assertions.assertThat(updatedDepartment).isNotNull();
	}

	@Test
	void testDeleteDepartment_ReturnsVoid() {
		// Arrange
		long departmentId = 1L;
		Department dept = new Department();
		dept.setId(departmentId);
		dept.setDeptName("HR");

		// Act
		doNothing().when(repo).delete(dept);
		service.deleteDepartment(dept);

		// Assert
		assertAll(() -> service.deleteDepartment(dept));
	}
}
