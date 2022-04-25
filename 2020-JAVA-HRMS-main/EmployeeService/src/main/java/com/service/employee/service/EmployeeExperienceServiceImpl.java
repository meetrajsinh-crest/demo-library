package com.service.employee.service;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.employee.dao.IEmployeeExperienceDao;
import com.service.employee.domain.Employee;
import com.service.employee.domain.EmployeeExperienceDetails;
import com.service.employee.domain.EmployeeExperienceModel;

@Service
public class EmployeeExperienceServiceImpl implements IEmployeeExperienceService {

	@Autowired
	IEmployeeExperienceDao dao;

	public List<EmployeeExperienceModel> getEmployeeExperience(long empId) {

		List<EmployeeExperienceDetails> employeeExperienceList = dao.findAllByEmpId(empId);
		return employeeExperienceList.stream().map(employeeExperience -> createEmployeeExperienceModel(employeeExperience)).collect(Collectors.toList());
	}

	private EmployeeExperienceModel createEmployeeExperienceModel(EmployeeExperienceDetails e) {
		EmployeeExperienceModel employeeExperienceModel = new EmployeeExperienceModel();
		employeeExperienceModel.setId(e.getId());
		employeeExperienceModel.setEmpId(e.getEmployee().getEmpId());
		employeeExperienceModel.setPreviousOrganizationName(e.getPreviousOrganizationName());
		employeeExperienceModel.setTitle(e.getTitle());
		employeeExperienceModel.setJoiningDate(e.getJoiningDate());
		employeeExperienceModel.setLeavingDate(e.getLeavingDate());
		
		Period diff  =  Period.between(new java.sql.Date(e.getJoiningDate().getTime()).toLocalDate(), 
				                       new java.sql.Date(e.getLeavingDate().getTime()).toLocalDate());
		employeeExperienceModel.setExperience(diff.getYears() + " Years "+ diff.getMonths() + " Months");
		return employeeExperienceModel;
	}

	public void addEmployeeExperience(EmployeeExperienceDetails employeeExperience, long empId) {
		validateAndInsertEmployee(employeeExperience, empId);
		dao.save(employeeExperience);
	}

	public void updateEmployeeExperience(EmployeeExperienceDetails employeeExperience, long empId) {
		validateAndInsertEmployee(employeeExperience, empId);
		dao.save(employeeExperience);
	}

	public void deleteEmployeeExperience(EmployeeExperienceDetails employeeExperience, long empId) {
		validateAndInsertEmployee(employeeExperience, empId);
		dao.delete(employeeExperience);
	}

	public void deleteEmployeeExperienceById(long empId, long id) {
		dao.deleteById(id);
	}
	
	private void validateAndInsertEmployee(EmployeeExperienceDetails employeeExperience, long empId) {
		if(employeeExperience.getEmployee() == null) {
			Employee e = new Employee();
			e.setEmpId(empId);
			employeeExperience.setEmployee(e);
		}
	}
}
