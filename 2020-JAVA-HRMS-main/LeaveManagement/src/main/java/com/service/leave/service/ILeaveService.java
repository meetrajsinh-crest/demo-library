package com.service.leave.service;

import java.util.List;

import com.service.leave.domain.LeaveDetails;
import com.service.leave.domain.LeaveDetailsModel;

public interface ILeaveService {

	public List<LeaveDetailsModel> getLeaveDetailsbyEmpId(long empId);
	
	public List<LeaveDetailsModel> getLeaveDetailsbyManagerId(long managerEmpId);
	
	public void applyLeave(LeaveDetails leaveDetails, long empId);
	
	public void updateAppliedLeave(LeaveDetails leaveDetails, long empId);
	
	public void cancelAppliedLeave(LeaveDetails leaveDetails);
	
	public void approveOrRejectLeave(LeaveDetails leaveDetails,long managerEmpId, String action);
}
