package com.service.leave.dao;

import java.util.List;

import com.service.leave.domain.Employee;
import com.service.leave.domain.LeaveDetails;

public interface ILeaveDao extends IGenericDao<LeaveDetails> {

	public List<LeaveDetails> getLeaveDetailsByEmployee(long empId);
	
	public List<LeaveDetails> getLeaveDetailsByManagerId(long managerEmpId);
	
	public Employee getEmployeeDetail(long empId);
	
	public void updateEmployeeLeaveBalance(double leaveBalance, long empId);
}
