package com.service.leave.controller;

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

import com.service.leave.common.APIResponse;
import com.service.leave.common.BaseRestController;
import com.service.leave.domain.LeaveDetails;
import com.service.leave.domain.LeaveDetailsModel;
import com.service.leave.service.ILeaveService;

@RestController
@RequestMapping(path ="/v1.0/leave/employee", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LeaveController extends BaseRestController {
	
	@Autowired
	private ILeaveService leaveService;
	
	
	@RequestMapping(path = "/{empId}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getLeaveDetailsByEmpId(@PathVariable("empId") long empId) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<LeaveDetailsModel>> getEmployeeList = () -> leaveService.getLeaveDetailsbyEmpId(empId);
		
		BiConsumer<List<LeaveDetailsModel>, Throwable> processResult = (result, exception) -> {
            if (exception != null) {
                deferred.setErrorResult(buildErrorResponse("ERROR_GET_LEAVE_DETAILS",exception.getMessage()));
            } else {
                deferred.setResult(buildResponse(result));
            }
		};
		
       CompletableFuture.supplyAsync(getEmployeeList, executor).whenComplete(processResult);
       return deferred;
    }
	
	@RequestMapping(path = "/{empId}/apply", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<APIResponse>> applyLeave(@PathVariable("empId") long empId, @RequestBody LeaveDetails leaveDetails) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> leaveService.applyLeave(leaveDetails,empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_APPLY_LEAVE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/{empId}/change", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<APIResponse>> updateAppliedLeave(@PathVariable("empId") long empId, @RequestBody LeaveDetails leaveDetails) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> leaveService.updateAppliedLeave(leaveDetails,empId), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_UPDATE_LEAVE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }
	
	@RequestMapping(path = "/{empId}/cancel", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<APIResponse>> cancelAppliedLeave(@PathVariable("empId") long empId, @RequestBody LeaveDetails leaveDetails) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> leaveService.cancelAppliedLeave(leaveDetails), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_UPDATE_LEAVE",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }

}
