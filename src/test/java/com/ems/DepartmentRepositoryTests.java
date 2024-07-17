package com.edm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.edm.model.Department;
import com.edm.repository.DepartmentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DepartmentRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private DepartmentRepository repo;

	@Test
	public void testCreateDepartment() {
		// Arrange
		Department dept = new Department();
		dept.setDeptName("HR");

		// Act
		Department savedDept = repo.save(dept);

		// Assert
		Department existDept = entityManager.find(Department.class, savedDept.getId());
		assertThat(savedDept).isNotNull();
		assertThat(existDept).isNotNull();
		assertThat(savedDept.getDeptName()).isEqualTo(existDept.getDeptName());
	}
}
