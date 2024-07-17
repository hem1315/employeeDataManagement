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

import com.edm.model.Department;
import com.edm.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.edm.model.Employee;
import com.edm.repository.EmployeeRepository;
import com.edm.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	void shouldReturnAllEmployees() {
		// Given
		List<Employee> mockEmployeeList = new ArrayList<>();
		Department itDept = new Department("IT");
		Department hrDept = new Department("HR");
		departmentRepository.save(itDept);
		departmentRepository.save(hrDept);

		Employee employee1 = new Employee(1L, "John", "Doe", new Department(1L, "IT"));
		Employee employee2 = new Employee(2L, "Jane", "Smith", new Department(2L, "HR"));
		mockEmployeeList.add(employee1);
		mockEmployeeList.add(employee2);

		// When
		when(employeeRepository.findAll()).thenReturn(mockEmployeeList);
		List<Employee> employeeList = employeeService.getAllEmployees();

		// Then
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
		assertThat(employeeList).containsExactlyInAnyOrder(employee1, employee2);
	}

	@Test
	void shouldReturnEmployeeByIdWhenExists() {
		// Given
		long id = 1L;
		Employee employee = new Employee(id, "John", "Doe", new Department(1L, "IT"));

		// When
		when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
		Optional<Employee> foundEmployee = employeeService.getById(id);

		// Then
		assertTrue(foundEmployee.isPresent());
		assertSame(foundEmployee.get(), employee);
	}

	@Test
	void shouldReturnEmptyOptionalWhenEmployeeDoesNotExist() {
		// Given
		long employeeId = 2L;

		// When
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
		Optional<Employee> result = employeeService.getById(employeeId);

		// Then
		assertFalse(result.isPresent());
	}

	@Test
	void shouldCreateEmployeeAndReturnIt() {
		// Given
		Employee employee = new Employee();
		employee.setFirstName("John");
		employee.setLastName("Doe");

		// When
		when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
		Employee savedEmployee = employeeService.saveEmployee(employee);

		// Then
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getFirstName()).isEqualTo("John");
		assertThat(savedEmployee.getLastName()).isEqualTo("Doe");
	}

	@Test
	void shouldUpdateEmployeeAndReturnIt() {
		// Given
		long employeeId = 1L;
		Employee employee = new Employee();
		employee.setId(employeeId);
		employee.setFirstName("John");
		employee.setLastName("Doe");

		// When
		when(employeeRepository.save(employee)).thenReturn(employee);
		Employee updatedEmployee = employeeService.saveEmployee(employee);

		// Then
		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getId()).isEqualTo(employeeId);
		assertThat(updatedEmployee.getFirstName()).isEqualTo("John");
		assertThat(updatedEmployee.getLastName()).isEqualTo("Doe");
	}

	@Test
	void shouldDeleteEmployee() {
		// Given
		long employeeId = 1L;
		Employee employee = new Employee();
		employee.setId(employeeId);
		employee.setFirstName("John");
		employee.setLastName("Doe");

		// When
		doNothing().when(employeeRepository).delete(employee);
		employeeService.deleteEmployee(employee);

		// Then
		assertAll(() -> employeeService.deleteEmployee(employee));
	}
}
