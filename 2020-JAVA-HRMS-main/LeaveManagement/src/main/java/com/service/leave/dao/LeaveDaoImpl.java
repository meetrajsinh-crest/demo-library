package com.service.leave.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.service.leave.domain.Employee;
import com.service.leave.domain.LeaveDetails;

@Repository
public class LeaveDaoImpl extends AbstractGenericDao<LeaveDetails> implements ILeaveDao {

	@SuppressWarnings("unchecked")
	public List<LeaveDetails> getLeaveDetailsByEmployee(long empId) {
		
		String hql = "FROM LeaveDetails where employee.empId = :empId order by appliedOn desc";
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("empId",empId);
		return ( List<LeaveDetails>)queryForObject(hql, parameterMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<LeaveDetails> getLeaveDetailsByManagerId(long managerEmpId) {
		
		String hql = "FROM LeaveDetails where employee.manager.empId = :empId order by appliedOn";
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("empId",managerEmpId);
		return ( List<LeaveDetails>)queryForObject(hql, parameterMap);
	}
	
	@SuppressWarnings("rawtypes")
	public Employee getEmployeeDetail(long empId) {
		
		String hql = "FROM Employee e where empId = :empId";
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("empId",empId);
		List results = queryForObject(hql, parameterMap);
		return results.size() > 0 ? (Employee)results.get(0) : new Employee();
		
	}
    
    public void updateEmployeeLeaveBalance(double leaveBalance, long empId) {
    	String hql = "update Employee set leaveBalance = :LEAVE_BALANCE where empId = :EMP_ID";
    	Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("LEAVE_BALANCE",leaveBalance);
		parameterMap.put("EMP_ID",empId);
		queryForInserUpdateDelete(hql, parameterMap);
    }
    
}
