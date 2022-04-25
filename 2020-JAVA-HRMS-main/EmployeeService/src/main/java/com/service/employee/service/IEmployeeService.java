package com.service.employee.service;

import java.util.List;

import com.service.employee.domain.Employee;
import com.service.employee.domain.EmployeeModel;


public interface IEmployeeService {
	
	
	public List<EmployeeModel> getEmployeeList(String sortByColumn, String sortByDirection);
	
	public List<EmployeeModel> getEmployeeById(long empId);
	
	public void addEmployee(Employee employee);
	
	public void updateEmployee(Employee employee);
	
	public void deleteEmployee(Employee employee);
	
	public void deleteEmployeeById(long empId);

}
