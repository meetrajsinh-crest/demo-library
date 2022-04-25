package com.service.leave.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.service.leave.dao.ILeaveDao;
import com.service.leave.domain.Employee;
import com.service.leave.domain.LeaveDetails;
import com.service.leave.domain.LeaveDetailsModel;

@Service
@Transactional
public class LeaveServiceImpl implements ILeaveService {

	@Autowired
	private ILeaveDao leaveDao;
	
	public List<LeaveDetailsModel> getLeaveDetailsbyEmpId(long empId){
		List<LeaveDetails> leaveDetailsList =  leaveDao.getLeaveDetailsByEmployee(empId);
		return leaveDetailsList.stream().map(leaveDetails-> createLeaveDetailsModel(leaveDetails)).collect(Collectors.toList());
	}
	
	public List<LeaveDetailsModel> getLeaveDetailsbyManagerId(long managerEmpId){
		List<LeaveDetails> leaveDetailsList =  leaveDao.getLeaveDetailsByManagerId(managerEmpId);
		return leaveDetailsList.stream().map(leaveDetails-> createLeaveDetailsModel(leaveDetails)).collect(Collectors.toList());
	}

	private LeaveDetailsModel createLeaveDetailsModel(LeaveDetails leaveDetails) {
		LeaveDetailsModel leaveDetailsModel = new LeaveDetailsModel();
		leaveDetailsModel.setLeaveId(leaveDetails.getLeaveId());
		leaveDetailsModel.setEmpId(leaveDetails.getEmployee().getEmpId());
		leaveDetailsModel.setEmployeeName(leaveDetails.getEmployee().getFirstName()+" "+leaveDetails.getEmployee().getLastName());
		leaveDetailsModel.setAppliedOn(leaveDetails.getAppliedOn());
		leaveDetailsModel.setFromDate(leaveDetails.getFromDate());
		leaveDetailsModel.setToDate(leaveDetails.getToDate());
		leaveDetailsModel.setFromDateHalfDay(leaveDetails.getFromDateHalfDay());
		leaveDetailsModel.setToDateHalfDay(leaveDetails.getToDateHalfDay());
		leaveDetailsModel.setLeaveDuration(leaveDetails.getLeaveDuration());
		leaveDetailsModel.setStatus(leaveDetails.getStatus());
		leaveDetailsModel.setReason(leaveDetails.getReason());
		leaveDetailsModel.setRemark(leaveDetails.getRemark());
		leaveDetailsModel.setActionBy(leaveDetails.getActionBy());
		
		return leaveDetailsModel;
	}
	
	
	public void applyLeave(LeaveDetails leaveDetails, long empId) {
		leaveDetails = validateAndCalculateLeaveBalance(leaveDetails, empId, "APPLY");
		leaveDao.saveOrUpdate(leaveDetails);
		leaveDao.updateEmployeeLeaveBalance(leaveDetails.getEmployee().getLeaveBalance(), empId);
		sendNotificationEmailToManager(leaveDetails.getEmployee());
	}
	
	public void updateAppliedLeave(LeaveDetails leaveDetails, long empId) {
		if("PENDING".equals(leaveDetails.getStatus())) {
			leaveDetails = validateAndCalculateLeaveBalance(leaveDetails, empId, "UPDATE");
			leaveDao.saveOrUpdate(leaveDetails);
			leaveDao.updateEmployeeLeaveBalance(leaveDetails.getEmployee().getLeaveBalance(), empId);
			sendNotificationEmailToManager(leaveDetails.getEmployee());
		}
		else
			throw new RuntimeException("Cannot Update NON Pending Requests");
	}
	
	public void cancelAppliedLeave(LeaveDetails leaveDetails) {
		if(!"REJECTED".equals(leaveDetails.getStatus()))
			approveRejectOrCancelLeave(leaveDetails, 0, "CANCELLED");
	}
	
	public void approveOrRejectLeave(LeaveDetails leaveDetails,long managerEmpId, String action) {
		if("PENDING".equals(leaveDetails.getStatus()))
			approveRejectOrCancelLeave(leaveDetails, managerEmpId, action);
	}
	
	private void approveRejectOrCancelLeave(LeaveDetails leaveDetails,long managerEmpId, String action)
	{
		String remark = leaveDetails.getRemark();
		leaveDetails.setRemark(remark);
		leaveDetails.setStatus(action);
		
		if("APPROVED".equals(action))
			approveLeave(leaveDetails, managerEmpId);
		else if("REJECTED".equals(action))
			rejectLeave(leaveDetails,managerEmpId);
		else if("CANCELLED".equals(action))
			cancelLeave(leaveDetails);
	}
	
	private void approveLeave(LeaveDetails leaveDetails, long managerEmpId){
		Employee manager = leaveDao.getEmployeeDetail(managerEmpId);
		leaveDetails.setActionBy(manager.getFirstName() +" "+manager.getLastName());
		leaveDao.saveOrUpdate(leaveDetails);
	}
	
	private void rejectLeave(LeaveDetails leaveDetails,long managerEmpId){
		leaveDetails = leaveDao.findById(leaveDetails.getLeaveId());
		leaveDetails.setStatus("REJECTED");
		Employee manager = leaveDetails.getEmployee().getManager();
		leaveDetails.setActionBy(manager.getFirstName() +" "+manager.getLastName());
		leaveDao.updateEmployeeLeaveBalance(leaveDetails.getEmployee().getLeaveBalance() + leaveDetails.getLeaveDuration(), 
				                            leaveDetails.getEmployee().getEmpId());
		leaveDao.saveOrUpdate(leaveDetails);
	}
	
	private void cancelLeave(LeaveDetails leaveDetails){
		leaveDetails = leaveDao.findById(leaveDetails.getLeaveId());
		leaveDetails.setActionBy("EMPLOYEE");
		leaveDetails.setStatus("CANCELLED");
		leaveDao.updateEmployeeLeaveBalance(leaveDetails.getEmployee().getLeaveBalance() + leaveDetails.getLeaveDuration(), 
				                            leaveDetails.getEmployee().getEmpId());
		leaveDao.saveOrUpdate(leaveDetails);
	}
	
	
	
	private void sendNotificationEmailToManager(Employee employee) {
		if(employee.getManager() != null)
		{
			Employee manager = leaveDao.getEmployeeDetail(employee.getManager().getEmpId());
			String managerEmailAddress = manager.getEmailAddress();
			String managerName = manager.getFirstName()+" "+manager.getLastName();
		
			System.out.println("Email will be sent to your Manager "+managerName+" Email Address : "+ managerEmailAddress);
		}
	}
	
	private LeaveDetails validateAndCalculateLeaveBalance(LeaveDetails leaveDetails, long empId, String calledFrom) {
		Employee employee = leaveDao.getEmployeeDetail(empId);
		Date fromDate = leaveDetails.getFromDate();
		Date toDate = leaveDetails.getToDate();
		
		//Calculate leave Duration
		double durationDays = (double)TimeUnit.DAYS.convert((toDate.getTime() - fromDate.getTime()), TimeUnit.MILLISECONDS)+1;
		
		//validate inputs
		if(durationDays < 1)
			throw new RuntimeException("Wrong date selection");
		else if(leaveDetails.getFromDateHalfDay() && leaveDetails.getToDateHalfDay() && durationDays == 1)
			throw new RuntimeException("Either from Date half day or to date half day should be true");
		
		
		if(leaveDetails.getFromDateHalfDay())
			durationDays = durationDays - 0.5;
		if(leaveDetails.getToDateHalfDay())
			durationDays = durationDays - 0.5;

		//Validate leave Duration with available leave balance
		double availableLeaveBalance = employee.getLeaveBalance();
		
		if("UPDATE".equals(calledFrom)) {
			LeaveDetails ld = leaveDao.findById(leaveDetails.getLeaveId());
			availableLeaveBalance = availableLeaveBalance + ld.getLeaveDuration();
		}
		
		availableLeaveBalance = availableLeaveBalance - durationDays;
		employee.setLeaveBalance(availableLeaveBalance);
		leaveDetails.setEmployee(employee);
		leaveDetails.setLeaveDuration(durationDays);
		leaveDetails.setStatus("PENDING");
		
		/*if(availableLeaveBalance >= durationDays) {
			availableLeaveBalance = availableLeaveBalance - durationDays;
			employee.setLeaveBalance(availableLeaveBalance);
			leaveDetails.setEmployee(employee);
			leaveDetails.setLeaveDuration(durationDays);
			leaveDetails.setStatus("PENDING");
		}
		else
			throw new RuntimeException("No Leave Balance available");*/
		
		return leaveDetails;
	}
}
