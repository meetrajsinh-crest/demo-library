package com.service.activity.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LeaveDetailsModel {
	
	private long leaveId;
	private long empId;
	private String employeeName;
	private Date appliedOn;
	private Date fromDate;
	private Boolean FromDateHalfDay;
	private Date toDate;
	private Boolean toDateHalfDay;
	private Double leaveDuration;
	private String reason;
	private String status;
	private String actionBy;
	private String remark;
	public long getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(long leaveId) {
		this.leaveId = leaveId;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public Date getAppliedOn() {
		return appliedOn;
	}
	public void setAppliedOn(Date appliedOn) {
		this.appliedOn = appliedOn;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Boolean getFromDateHalfDay() {
		return FromDateHalfDay;
	}
	public void setFromDateHalfDay(Boolean fromDateHalfDay) {
		FromDateHalfDay = fromDateHalfDay;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Boolean getToDateHalfDay() {
		return toDateHalfDay;
	}
	public void setToDateHalfDay(Boolean toDateHalfDay) {
		this.toDateHalfDay = toDateHalfDay;
	}
	public Double getLeaveDuration() {
		return leaveDuration;
	}
	public void setLeaveDuration(Double leaveDuration) {
		this.leaveDuration = leaveDuration;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActionBy() {
		return actionBy;
	}
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
}
