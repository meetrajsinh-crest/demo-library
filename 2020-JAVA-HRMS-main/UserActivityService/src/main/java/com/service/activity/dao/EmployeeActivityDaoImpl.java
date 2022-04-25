package com.service.activity.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.service.activity.domain.EmployeeActivity;

@Repository
public class EmployeeActivityDaoImpl extends AbstractGenericDao<EmployeeActivity> implements IEmployeeActivityDao {

	@Autowired
    private HibernateTemplate hibernateTemplate;
	
	public List<EmployeeActivity> getAllActivities()
    {
        return this.hibernateTemplate.loadAll(EmployeeActivity.class);
    }
	
	@SuppressWarnings("unchecked")
	public List<EmployeeActivity> getEmployeeActivitiesByEmpId(long empId)
	{
		String hql = "FROM EmployeeActivity where employee.empId = :empId order by checkInTime ";
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("empId",empId);
		return (List<EmployeeActivity>)queryForObject(hql, parameterMap);
	}
	
	@SuppressWarnings("unchecked")
	public EmployeeActivity getLastCheckInByEmpId(long empId)
	{
		String hql = "FROM EmployeeActivity where employee.empId = :empId and checkInTime is not null and checkInTime >= :currentDate order by checkInTime desc";
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("empId",empId);
		Long time = new Date().getTime();
		parameterMap.put("currentDate",new Date(time - time % (24 * 60 * 60 * 1000)));
		
		List<EmployeeActivity> results = queryForObject(hql, parameterMap);
		
		return results.size()>0 ? results.get(0): null ;
	}


}
