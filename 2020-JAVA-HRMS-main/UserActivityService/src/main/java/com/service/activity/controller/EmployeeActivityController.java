package com.service.activity.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.service.activity.common.APIResponse;
import com.service.activity.common.BaseRestController;
import com.service.activity.domain.EmployeeActivityModel;
import com.service.activity.service.IEmployeeActivityService;

@RestController
@RequestMapping(path ="/v1.0/activity", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EmployeeActivityController extends BaseRestController {
	
	@Autowired
	IEmployeeActivityService employeeActivity;
	
	
	@RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getAllEmployeeActivity() {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<EmployeeActivityModel>> getEmployeeList = () -> employeeActivity.getAllEmployeeActivity();
		
		BiConsumer<List<EmployeeActivityModel>, Throwable> processResult = (result, exception) -> {
            if (exception != null) {
                deferred.setErrorResult(buildErrorResponse("ERROR_GET_ALL_ACTIVITY_DETAILS",exception.getMessage()));
            } else {
                deferred.setResult(buildResponse(result));
            }
		};
		
       CompletableFuture.supplyAsync(getEmployeeList, executor).whenComplete(processResult);
       return deferred;
    }
	
	@RequestMapping(path = "/employee/{empId}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getActivityByEmpId(@PathVariable("empId") long empId) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<EmployeeActivityModel>> getEmployeeList = () -> employeeActivity.getEmployeeActivityByEmployeeId(empId);
		
		BiConsumer<List<EmployeeActivityModel>, Throwable> processResult = (result, exception) -> {
            if (exception != null) {
                deferred.setErrorResult(buildErrorResponse("ERROR_GET_EMPLOYEE_ACTIVITY_DETAILS",exception.getMessage()));
            } else {
                deferred.setResult(buildResponse(result));
            }
		};
		
       CompletableFuture.supplyAsync(getEmployeeList, executor).whenComplete(processResult);
       return deferred;
    }
	
	@RequestMapping(path = "/employee/{empId}/checkIn", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<APIResponse>> employeeCheckin(@PathVariable("empId") long empId) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeActivity.performEmployeeCheckIn(empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_EMPLOYEE_CHECK_IN",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
		
       return deferred;
    }
	
	@RequestMapping(path = "/employee/{empId}/checkOut", method = RequestMethod.PUT)
	public DeferredResult<ResponseEntity<APIResponse>> employeeCheckOut(@PathVariable("empId") long empId) {

		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();

		CompletableFuture.runAsync(() -> employeeActivity.performEmployeeCheckOut(empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_EMPLOYEE_CHECK_OUT",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
		return deferred;
	}

}
