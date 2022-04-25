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

import com.service.employee.domain.EmployeeExperienceDetails;
import com.service.employee.domain.EmployeeExperienceModel;
import com.service.employee.service.IEmployeeExperienceService;
import com.services.employee.common.APIResponse;
import com.services.employee.common.BaseRestController;

@RestController
@RequestMapping(path ="/v1.0/employee/{empId}", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EmployeeExperienceController extends BaseRestController {

	@Autowired
	IEmployeeExperienceService employeeExperienceservice;
	
	@RequestMapping(path = "/experience", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getEmployeeFamily(@PathVariable("empId") long empId) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<EmployeeExperienceModel>> getEmployeeList = () -> employeeExperienceservice.getEmployeeExperience(empId);
		
		BiConsumer<List<EmployeeExperienceModel>, Throwable> processResult = (result, exception) -> {
            if (exception != null) {
                deferred.setErrorResult(buildErrorResponse("ERROR_GET_EMPLOYEE_EXPERIENCE",exception.getMessage()));
            } else {
                deferred.setResult(buildResponse(result));
            }
		};
		
       CompletableFuture.supplyAsync(getEmployeeList, executor).whenComplete(processResult);
       return deferred;
    }
	
	@RequestMapping(path = "/experience", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<APIResponse>> addEmployeeExperience(@PathVariable("empId") long empId, 
    		                                                       @RequestBody EmployeeExperienceDetails employeeExperience) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeExperienceservice.addEmployeeExperience(employeeExperience, empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_ADD_EMPLOYEE_EXPERIENCE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/experience", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<APIResponse>> updateEmployeeExperience(@PathVariable("empId") long empId, 
                                                                      @RequestBody EmployeeExperienceDetails employeeExperience) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeExperienceservice.updateEmployeeExperience(employeeExperience, empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_UPDATE_EMPLOYEE_EXPERIENCE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/experience", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<APIResponse>> deleteEmployeeExperience(@PathVariable("empId") long empId, 
                                                                      @RequestBody EmployeeExperienceDetails employeeExperience) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeExperienceservice.deleteEmployeeExperience(employeeExperience, empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_DELETE_EMPLOYEE_EXPERIENCE", ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/experience/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<APIResponse>> deleteEmployeeExperienceById(@PathVariable("empId") long empId, @PathVariable("id")long id) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeExperienceservice.deleteEmployeeExperienceById(empId, id), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_DELETE_EMPLOYEE_EXPERIENCE", ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
}
