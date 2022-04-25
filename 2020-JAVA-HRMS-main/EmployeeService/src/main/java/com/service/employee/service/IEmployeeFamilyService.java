package com.service.employee.service;

import java.util.List;

import com.service.employee.domain.EmployeeFamilyDetails;
import com.service.employee.domain.EmployeeFamilyModel;

public interface IEmployeeFamilyService {
	
	public List<EmployeeFamilyModel> getEmployeeFamily(long empId);

	void addEmployeeFamily(EmployeeFamilyDetails employee, long empId);

	void updateEmployeeFamily(EmployeeFamilyDetails employee, long empId);

	void deleteEmployeeFamily(EmployeeFamilyDetails employee, long empId);

	void deleteEmployeeFamilyById(long empId, long id);

}
