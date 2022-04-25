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

@Entity
@Table(name="EMPLOYEE_EXPERIENCE_DETAILS")
public class EmployeeExperienceDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",length = 5)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID")
	private Employee employee;

	@Column(name = "ORGANIZATION_NAME", length = 40)
	private String previousOrganizationName;

	@Column(name = "JOINING_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date joiningDate;

	@Column(name = "LEAVING_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date leavingDate;
	
	@Column(name = "TITLE", length = 40)
	private String title;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPreviousOrganizationName() {
		return previousOrganizationName;
	}

	public void setPreviousOrganizationName(String previousOrganizationName) {
		this.previousOrganizationName = previousOrganizationName;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
