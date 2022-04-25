package com.service.employee.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.service.employee.dao.IEmployeeDao;
import com.service.employee.domain.Employee;

@Component
public class LeaveCreditScheduler {

	@Autowired
	private IEmployeeDao dao;
	
	@Scheduled(cron = "0 0 1 * *")
	public void cronJobleaveCredit() {
		List<Employee> employeeList = dao.findAll();
		employeeList.forEach(emplyee-> creditLeave(emplyee));
	   }
	
	
	private void creditLeave(Employee e) {
		/*double credit = 1.75;
		Date joiningDate = e.getJoiningDate();
		Date currentDate = new Date();
		double durationDays = (double)TimeUnit.DAYS.convert((currentDate.getTime() - joiningDate.getTime()), TimeUnit.MILLISECONDS)+1;
		if(durationDays >= 1095)
			credit = 2.0;
		e.setLeaveBalance(e.getLeaveBalance() + credit);
		dao.save(e);*/
		
		System.out.println("Employee : "+ e.getFirstName() +"  Leave Balance :"+ e.getLeaveBalance());
	}
	
}
