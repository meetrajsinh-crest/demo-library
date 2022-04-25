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
@RequestMapping(path ="/v1.0/leave/manager", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LeaveApprovalController extends BaseRestController {
	
	@Autowired
	private ILeaveService leaveService;
	
	
	@RequestMapping(path = "/{managerEmpId}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<APIResponse>> getLeaveDetailsByEmpId(@PathVariable("managerEmpId") long managerEmpId) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		Supplier<List<LeaveDetailsModel>> getEmployeeList = () -> leaveService.getLeaveDetailsbyManagerId(managerEmpId);
		
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
	
	@RequestMapping(path = "/{managerEmpId}/action/{action}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<APIResponse>> approveRejectOrCancelLeave(@PathVariable("managerEmpId") long managerEmpId,
    		                                                                      @PathVariable("action") String action, 
    																			  @RequestBody LeaveDetails leaveDetails) {
		DeferredResult<ResponseEntity<APIResponse>> deferred = new DeferredResult<>();
		
		CompletableFuture.runAsync(() -> leaveService.approveOrRejectLeave(leaveDetails, managerEmpId, action), executor).whenComplete((res,ex) -> {
			if(ex != null) {
				deferred.setErrorResult(buildErrorResponse("ERROR_LEAVE_ACTION",ex.getMessage()));
			}
			else {
				deferred.setResult(buildEmptyResponse());
			}
		});
       return deferred;
    }

}
