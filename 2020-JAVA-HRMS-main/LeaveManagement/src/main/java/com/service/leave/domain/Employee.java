package com.service.leave.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

@Entity
@Table(name="EMPLOYEE")
public class Employee implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_ID",length = 5)
	private long empId;

	@Column(name = "FIRST_NAME", length = 40)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 40)
	private String lastName;

	@Column(name = "BIRTH_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date birthDate;

	@Column(name = "GENDER",length = 10)
	private String gender;

	@Column(name="CURRENT_ADDRESS", length = 255)
	private String currentAddress;

	@Column(name="PERMANENT_ADDRESS", length = 255)
	private String permanentAddress;

	@Column(name="CONTACT_NO", length = 10)
	private String contactNo;

	@Column(name="EMAIL_ADDRESS",unique = true, length = 40)
	@NotNull
	private String emailAddress;

	@Column(name = "JOINING_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date joiningDate;

	@Column(name = "DESIGNATION",length = 40)
	private String designation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT_ID")
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_EMP_ID")
	private Employee manager; 
	
	@Column(name = "LEAVE_BALANCE", length = 5, columnDefinition = "double default 0")
	private Double leaveBalance;
	
	@OneToMany(mappedBy = "manager")
	private Set<Employee> managedEmployeeSet;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private Set<EmployeeExperienceDetails> employeeExperienceDetails;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private Set<EmployeeFamilyDetails> employeeFamilyDetails;
	
	@Transient
	private long deptId;
	
	@Transient
	private long managerEmpId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(Double leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Set<Employee> getManagedEmployeeSet() {
		return managedEmployeeSet;
	}

	public void setManagedEmployeeSet(Set<Employee> managedEmployeeSet) {
		this.managedEmployeeSet = managedEmployeeSet;
	}

	public Set<EmployeeExperienceDetails> getEmployeeExperienceDetails() {
		return employeeExperienceDetails;
	}

	public void setEmployeeExperienceDetails(Set<EmployeeExperienceDetails> employeeExperienceDetails) {
		this.employeeExperienceDetails = employeeExperienceDetails;
	}

	public Set<EmployeeFamilyDetails> getEmployeeFamilyDetails() {
		return employeeFamilyDetails;
	}

	public void setEmployeeFamilyDetails(Set<EmployeeFamilyDetails> employeeFamilyDetails) {
		this.employeeFamilyDetails = employeeFamilyDetails;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public long getManagerEmpId() {
		return managerEmpId;
	}

	public void setManagerEmpId(long managerEmpId) {
		this.managerEmpId = managerEmpId;
	}
	
}