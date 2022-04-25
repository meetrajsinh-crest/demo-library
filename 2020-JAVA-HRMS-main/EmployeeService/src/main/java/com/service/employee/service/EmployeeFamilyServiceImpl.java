package com.service.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.employee.dao.IEmployeeFamilyDao;
import com.service.employee.domain.Employee;
import com.service.employee.domain.EmployeeFamilyDetails;
import com.service.employee.domain.EmployeeFamilyModel;

@Service
public class EmployeeFamilyServiceImpl implements IEmployeeFamilyService {
	
	@Autowired
	IEmployeeFamilyDao dao;
	
	
	public List<EmployeeFamilyModel> getEmployeeFamily(long empId) {
		List<EmployeeFamilyDetails> employeeFamilyList = dao.findAllByEmpId(empId);
		return employeeFamilyList.stream().map(employeeFamily -> createEmployeeFamilyModel(employeeFamily)).collect(Collectors.toList());
	}
	
	private EmployeeFamilyModel createEmployeeFamilyModel(EmployeeFamilyDetails e) {
		EmployeeFamilyModel employeeFamilyModel = new EmployeeFamilyModel();
		employeeFamilyModel.setId(e.getId());
		employeeFamilyModel.setEmpId(e.getEmployee().getEmpId());
		employeeFamilyModel.setName(e.getName());
		employeeFamilyModel.setRelation(e.getRelation());
		employeeFamilyModel.setContactNo(e.getContactNo());
		employeeFamilyModel.setBirthDate(e.getBirthDate());
		return employeeFamilyModel;
	}
	
	@Override
	public void addEmployeeFamily(EmployeeFamilyDetails employeeFamily, long empId) {
		validateAndInsertEmployee(employeeFamily, empId);
		dao.save(employeeFamily);
	}
	
	@Override
	public void updateEmployeeFamily(EmployeeFamilyDetails employeeFamily, long empId) {
		validateAndInsertEmployee(employeeFamily, empId);
		dao.save(employeeFamily);
	}
	
	@Override
	public void deleteEmployeeFamily(EmployeeFamilyDetails employeeFamily, long empId) {
		validateAndInsertEmployee(employeeFamily, empId);
		dao.delete(employeeFamily);
	}
	
	@Override
	public void deleteEmployeeFamilyById(long empId, long id) {
		dao.deleteById(id);
	}

	private void validateAndInsertEmployee(EmployeeFamilyDetails employeeFamily, long empId) {
		if(employeeFamily.getEmployee() == null) {
			Employee e = new Employee();
			e.setEmpId(empId);
			employeeFamily.setEmployee(e);
		}
	}
}
