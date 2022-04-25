package com.service.employee.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.service.employee.domain.EmployeeFamilyDetails;
import com.service.employee.domain.EmployeeFamilyModel;
import com.service.employee.service.IEmployeeFamilyService;
import com.services.employee.common.APIResponse;
import com.services.employee.common.BaseRestController;

@RestController
@RequestMapping(path ="/v1.0/employee/{empId}", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EmployeeFamilyController extends BaseRestController {
	
	@Autowired
	IEmployeeFamilyService employeeFamilyservice;
	
	@RequestMapping(path = "/family", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getEmployeeFamily(@PathVariable("empId") long empId) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<EmployeeFamilyModel>> getEmployeeList = () -> employeeFamilyservice.getEmployeeFamily(empId);
		
		BiConsumer<List<EmployeeFamilyModel>, Throwable> processResult = (result, exception) -> {
            if (exception != null) {
            	deferred.setErrorResult(buildErrorResponse("ERROR_GET_EMPLOYEE_FAMILY", exception.getMessage()));
            } else {
                deferred.setResult(buildResponse(result));
            }
		};
		
       CompletableFuture.supplyAsync(getEmployeeList, executor).whenComplete(processResult);
       return deferred;
    }
	
	@RequestMapping(path = "/family", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<APIResponse>> addEmployeeFamily(@PathVariable("empId") long empId, 
    		                                                       @RequestBody EmployeeFamilyDetails employeeFamily) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeFamilyservice.addEmployeeFamily(employeeFamily, empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_ADD_EMPLOYEE_FAMILY", ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/family", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<APIResponse>> updateEmployeeFamily(@PathVariable("empId") long empId, 
                                                                      @RequestBody EmployeeFamilyDetails employeeFamily) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeFamilyservice.updateEmployeeFamily(employeeFamily, empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_UPDATE_EMPLOYEE_FAMILY", ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/family", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<APIResponse>> deleteEmployeeFamily(@PathVariable("empId") long empId, 
                                                                      @RequestBody EmployeeFamilyDetails employeeFamily) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeFamilyservice.deleteEmployeeFamily(employeeFamily, empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_DELETE_EMPLOYEE_FAMILY", ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/family/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<APIResponse>> deleteEmployeeFamilyById(@PathVariable("empId") long empId, @PathVariable("id")long id) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeFamilyservice.deleteEmployeeFamilyById(empId, id), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_DELETE_EMPLOYEE_FAMILY", ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	

}
