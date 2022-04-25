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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.service.employee.domain.Employee;
import com.service.employee.domain.EmployeeModel;
import com.service.employee.service.IEmployeeService;
import com.services.employee.common.APIResponse;
import com.services.employee.common.BaseRestController;

@RestController
@RequestMapping(path ="/v1.0/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EmployeeController extends BaseRestController {

	@Autowired
	private IEmployeeService employeeService;
	
	@RequestMapping(path = "employee", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getEmployeeList(@RequestParam(name="sortByColumn",required = false) String sortByColumn,
    																   @RequestParam(name="sortByDir", required = false) String sortByDirection) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<EmployeeModel>> getEmployeeList = () -> employeeService.getEmployeeList(sortByColumn, sortByDirection);
		
		BiConsumer<List<EmployeeModel>, Throwable> processResult = (result, exception) -> {
            if (exception != null) {
                deferred.setErrorResult(buildErrorResponse("ERROR_GET_EMPLOYEE_LIST",exception.getMessage()));
            } else {
                deferred.setResult(buildResponse(result));
            }
		};
		
       CompletableFuture.supplyAsync(getEmployeeList, executor).whenComplete(processResult);
       return deferred;
    }
	
	@RequestMapping(path = "employee/{id}", method = RequestMethod.GET)
	public DeferredResult<ResponseEntity<APIResponse>> getEmployeeList(@PathVariable("id") long empId) {
		
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<EmployeeModel>> getEmployee = () -> employeeService.getEmployeeById(empId);
		
		BiConsumer<List<EmployeeModel>, Throwable> processResult = (result, exception) -> {
	        if (exception != null) {
	            deferred.setErrorResult(buildErrorResponse( "ERROR_GET_EMPLOYEE",exception.getMessage()));
	        } else {
	            deferred.setResult(buildResponse(result));
	        }
		};
		
	   CompletableFuture.supplyAsync(getEmployee, executor).whenComplete(processResult);
	   return deferred;
	}

	@RequestMapping(path = "employee", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<APIResponse>> addEmployee(@RequestBody Employee employee) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeService.addEmployee(employee), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_ADD_EMPLOYEE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "employee", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<APIResponse>> updateEmployee(@RequestBody Employee employee) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeService.addEmployee(employee), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_UPDATE_EMPLOYEE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "employee", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<APIResponse>> deleteEmployee(@RequestBody Employee employee) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeService.deleteEmployee(employee), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_DELETE_EMPLOYEE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "employee/{id}", method = RequestMethod.DELETE)
    public DeferredResult<ResponseEntity<APIResponse>> deleteEmployee(@PathVariable("id")long empId) {
        
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> employeeService.deleteEmployeeById(empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_DELETE_EMPLOYEE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
}
