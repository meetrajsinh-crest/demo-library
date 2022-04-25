package com.service.employee.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.service.employee.domain.EmployeeExperienceDetails;

public interface IEmployeeExperienceDao extends JpaRepository<EmployeeExperienceDetails, Long> {

	  @Query("SELECT t FROM EmployeeExperienceDetails t WHERE t.employee.empId = ?1 Order by t.joiningDate desc")
	  List<EmployeeExperienceDetails> findAllByEmpId(long empId);
}
