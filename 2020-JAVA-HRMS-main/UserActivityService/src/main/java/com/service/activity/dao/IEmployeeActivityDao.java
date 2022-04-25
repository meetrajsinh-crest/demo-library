package com.service.activity.dao;

import java.util.List;

import com.service.activity.domain.EmployeeActivity;

public interface IEmployeeActivityDao extends IGenericDao<EmployeeActivity> {
	
	public List<EmployeeActivity> getAllActivities();
	
	public List<EmployeeActivity> getEmployeeActivitiesByEmpId(long empId);
	
	public EmployeeActivity getLastCheckInByEmpId(long empId);

}
