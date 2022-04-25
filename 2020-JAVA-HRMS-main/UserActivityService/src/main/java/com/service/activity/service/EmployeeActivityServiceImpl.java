package com.service.activity.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.activity.dao.IEmployeeActivityDao;
import com.service.activity.domain.Employee;
import com.service.activity.domain.EmployeeActivity;
import com.service.activity.domain.EmployeeActivityModel;


@Transactional
@Service
public class EmployeeActivityServiceImpl implements IEmployeeActivityService {
	
	@Autowired
	IEmployeeActivityDao dao;
	
	public List<EmployeeActivityModel> getAllEmployeeActivity(){
		List<EmployeeActivity> employeeActivityList =  dao.findAll();
		return employeeActivityList.stream().map(employeeActivity -> prepareEmployeeActivityModel(employeeActivity)).collect(Collectors.toList());
	}
	
	private EmployeeActivityModel prepareEmployeeActivityModel(EmployeeActivity e) {
		
		EmployeeActivityModel activityModel = new EmployeeActivityModel();
		activityModel.setActivityId(e.getActivityId());
		activityModel.setCheckInTime(e.getCheckInTime());
		activityModel.setCheckOutTime(e.getCheckOutTime());
		activityModel.setWorkDuration(e.getWorkDuration());
		activityModel.setEmployeeName(e.getEmployee().getFirstName());
		
		return activityModel;
	}
	
	public List<EmployeeActivityModel> getEmployeeActivityByEmployeeId(long empId){
		List<EmployeeActivity> employeeActivityList =  dao.getEmployeeActivitiesByEmpId(empId);
		return employeeActivityList.stream().map(employeeActivity -> prepareEmployeeActivityModel(employeeActivity)).collect(Collectors.toList());
	}
	
	public void performEmployeeCheckIn(long empId) {
		EmployeeActivity ea = new EmployeeActivity();
		Employee employee = new Employee();
		employee.setEmpId(empId);
		ea.setEmployee(employee);
		ea.setCheckInTime(new Date());
		dao.saveOrUpdate(ea);
	}
	
	public void performEmployeeCheckOut(long empId) {
		EmployeeActivity ea = dao.getLastCheckInByEmpId(empId);
		Date checkOutDate = new Date();
		if(ea != null) {
			Calendar checkInCal = Calendar.getInstance();
			checkInCal.setTimeInMillis(ea.getCheckInTime().getTime());
			Calendar checkOutCal = Calendar.getInstance();
			checkOutCal.setTimeInMillis(checkOutDate.getTime());
			if(isSameDay(checkInCal, checkOutCal) 
					&& ea.getCheckOutTime() == null ) {
				ea.setCheckOutTime(checkOutDate);
				long diffInMillies = Math.abs(checkOutDate.getTime() - ea.getCheckInTime().getTime());
				double hours = diffInMillies /(1000*3600);
				
			    //double workedHours = (double)TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    ea.setWorkDuration(hours);
			}
			else
				performNewCheckOut(empId);
		}
		else
			performNewCheckOut(empId);

	}
	
	private void performNewCheckOut(long empId) {
		EmployeeActivity ea = new EmployeeActivity();
		Employee employee = new Employee();
		employee.setEmpId(empId);
		ea.setEmployee(employee);
		ea.setCheckOutTime(new Date());
		dao.saveOrUpdate(ea);
		
	}
	
	private boolean isSameDay(final Calendar cal1, final Calendar cal2) {
		return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
				cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
				cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	}

}
