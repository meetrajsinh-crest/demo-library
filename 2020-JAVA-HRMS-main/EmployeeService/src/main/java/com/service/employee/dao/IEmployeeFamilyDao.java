package com.service.employee.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.service.employee.domain.EmployeeFamilyDetails;

public interface IEmployeeFamilyDao extends JpaRepository<EmployeeFamilyDetails, Long> {

	  @Query("SELECT t FROM EmployeeFamilyDetails t WHERE t.employee.empId = ?1 ")
	  List<EmployeeFamilyDetails> findAllByEmpId(long empId);
}
