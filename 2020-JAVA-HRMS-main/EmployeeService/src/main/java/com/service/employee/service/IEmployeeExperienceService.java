package com.service.employee.service;

import java.util.List;

import com.service.employee.domain.EmployeeExperienceDetails;
import com.service.employee.domain.EmployeeExperienceModel;

public interface IEmployeeExperienceService {
	
	public List<EmployeeExperienceModel> getEmployeeExperience(long empId); 
	
	public void addEmployeeExperience(EmployeeExperienceDetails employeeExperience, long empId);

	public void updateEmployeeExperience(EmployeeExperienceDetails employeeExperience, long empId);

	public void deleteEmployeeExperience(EmployeeExperienceDetails employeeExperience, long empId);
	
	public void deleteEmployeeExperienceById(long empId, long id);

}
