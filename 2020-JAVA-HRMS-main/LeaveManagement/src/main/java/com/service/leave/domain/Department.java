package com.service.leave.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;


@Entity
@Table(name="DEPARTMENT")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPT_ID",length = 3)
	private long deptId;
	
	@Column(name = "DEPT_NAME", unique=true, length = 40)
	@NotNull
	private String deptName;
	
	@OneToOne(targetEntity = Employee.class)
	private Employee deptHead;
	
	@Column(name = "DEPT_LOCATION",length = 40)
	private String  deptLocation;
	
	@OneToMany(mappedBy = "department")
	Set<Employee> employeeSet;
	
	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Employee getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(Employee deptHead) {
		this.deptHead = deptHead;
	}

	public String getDeptLocation() {
		return deptLocation;
	}

	public void setDeptLocation(String deptLocation) {
		this.deptLocation = deptLocation;
	}

	public Set<Employee> getEmployeeSet() {
		return employeeSet;
	}

	public void setEmployeeSet(Set<Employee> employeeSet) {
		this.employeeSet = employeeSet;
	}
}
