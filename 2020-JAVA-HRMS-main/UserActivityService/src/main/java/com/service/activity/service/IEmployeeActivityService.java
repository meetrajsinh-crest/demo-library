package com.service.activity.service;

import java.util.List;

import com.service.activity.domain.EmployeeActivityModel;

public interface IEmployeeActivityService {
	
	public List<EmployeeActivityModel> getAllEmployeeActivity();
	
	public List<EmployeeActivityModel> getEmployeeActivityByEmployeeId(long empId);
	
	public void performEmployeeCheckIn(long empId);
	
	public void performEmployeeCheckOut(long empId);

}
