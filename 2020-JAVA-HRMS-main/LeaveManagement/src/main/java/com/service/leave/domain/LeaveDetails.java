package com.service.leave.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="LEAVE_DETAILS")
@JsonInclude(value = Include.NON_NULL)
public class LeaveDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LEAVE_ID",length = 10)
	private long leaveId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID")
	private Employee employee;
	
	@Column(name = "APPLIED_ON")
	@Temporal(value=TemporalType.DATE)
	private Date appliedOn;
	
	@Column(name = "FROM_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date fromDate;
	
	@Column(name = "FROM_DATE_HALF_DAY", columnDefinition = "boolean default false")
	private Boolean FromDateHalfDay;
	
	@Column(name = "TO_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date toDate;
	
	@Column(name = "TO_DATE_HALF_DAY",columnDefinition = "boolean default false")
	private Boolean toDateHalfDay;
	
	@Column(name = "LEAVE_DURATION", length = 5)
	private Double leaveDuration;
	
	@Column(name = "REASON", length = 255)
	private String reason;
	
	@Column(name = "STATUS", length = 15)
	private String status;
	
	@Column(name = "ACTION_BY", length = 40)
	private String actionBy;
	
	@Column(name = "REMARK", length = 255)
	private String remark;

	public long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(long leaveId) {
		this.leaveId = leaveId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Boolean getFromDateHalfDay() {
		return FromDateHalfDay;
	}

	public void setFromDateHalfDay(Boolean fromDateHalfDay) {
		FromDateHalfDay = fromDateHalfDay;
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
		
}
