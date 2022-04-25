package com.service.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.service.employee.domain.Department;
import com.service.employee.domain.Employee;

public interface IEmployeeDao extends JpaRepository<Employee, Long> {
	
	@Query("SELECT t FROM DEPARTMENT t WHERE t.deptId = ?1")
	Department findDepartmentById(long deptId);
}
