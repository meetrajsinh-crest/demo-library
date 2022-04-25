package com.service.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.service.employee.dao.IEmployeeDao;
import com.service.employee.domain.Department;
import com.service.employee.domain.Employee;
import com.service.employee.domain.EmployeeModel;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	IEmployeeDao dao;
	
	public List<EmployeeModel> getEmployeeList(String sortByColumn, String sortByDirection) {
		sortByColumn = StringUtils.isEmpty(sortByColumn) ? "empId" : sortByColumn;
		Direction sortDir = "DESC".equalsIgnoreCase(sortByDirection)?Direction.DESC:Direction.ASC;
		List<Employee> employeeList = dao.findAll(Sort.by(sortDir,sortByColumn));
		
		return employeeList.stream().map(employee -> createEmployeeModel(employee)).collect(Collectors.toList());
		
	}
	
	private EmployeeModel createEmployeeModel(Employee e) {
		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setEmpId(e.getEmpId());
		employeeModel.setFirstName(e.getFirstName());
		employeeModel.setLastName(e.getLastName());
		employeeModel.setBirthDate(e.getBirthDate());
		employeeModel.setContactNo(e.getContactNo());
		employeeModel.setCurrentAddress(e.getCurrentAddress());
		employeeModel.setPermanentAddress(e.getPermanentAddress());
		employeeModel.setGender(e.getGender());
		employeeModel.setJoiningDate(e.getJoiningDate());
		employeeModel.setEmailAddress(e.getEmailAddress());
		employeeModel.setDesignation(e.getDesignation());
		employeeModel.setLeaveBalance(e.getLeaveBalance());
		
		if(e.getManager() != null) 
			employeeModel.setManagerName(e.getManager().getFirstName() +" "+e.getManager().getLastName());
		if(e.getDepartment() != null)
			employeeModel.setDeptName(e.getDepartment().getDeptName());
		
		return employeeModel;
	}
	
	@Override
	public List<EmployeeModel> getEmployeeById(long empId) {
		List<EmployeeModel> employeeList = new ArrayList<>();
		Employee employee =  dao.findById(empId).orElse(null);
		if(employee != null)
			employeeList.add(createEmployeeModel(employee));
		
		return employeeList;
	}
	
	
	@Override
	public void addEmployee(Employee employee) {
		if(employee.getLeaveBalance() == null)
			employee.setLeaveBalance(Double.valueOf(0));
		
		/*if(employee.getDepartment() == null 
				&& (Long)employee.getDeptId() != null){
			Department d = dao.findDepartmentById(employee.getDeptId());
			employee.setDepartment(d);
		}
		
		if(employee.getManager() == null 
				&& (Long)employee.getManagerEmpId() != null) {
			Employee manager = dao.findById(employee.getManagerEmpId()).get();
			//manager.setEmpId(employee.getManagerEmpId());
			employee.setManager(manager);
		}*/
		saveEmployee(employee);
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		if(employee.getLeaveBalance() == null)
			employee.setLeaveBalance(Double.valueOf(0));
		
		/*if(employee.getDepartment() == null 
				&& (Long)employee.getDeptId() != null) {
			Department d = dao.findDepartmentById(employee.getDeptId());
			employee.setDepartment(d);
		}
		
		if(employee.getManager() == null 
				&& (Long)employee.getManagerEmpId() != null) {
			Employee manager = new Employee();
			manager.setEmpId(employee.getManagerEmpId());
			employee.setManager(manager);
		}*/
		saveEmployee(employee);
		
	}
	
	private void saveEmployee(Employee employee)
	{
		dao.save(employee);
	}
	
	@Override
	public void deleteEmployee(Employee employee) {
		dao.delete(employee);
	}
	
	@Override
	public void deleteEmployeeById(long empId) {
		dao.deleteById(empId);
	}
	
}
