package com.service.leave.common;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class BaseRestController {

	@Autowired
	@Qualifier("applicationTaskExecutor")
	protected Executor executor;
	
	protected ResponseEntity<APIResponse> buildResponse(Object responseData) {
		APIResponse apiResponse = APIResponse.builder().data(responseData).build();
		return ResponseEntity.ok(apiResponse);
	}

	protected ResponseEntity<APIResponse> buildEmptyResponse() {
		APIResponse apiResponse = APIResponse.builder().build();
		return ResponseEntity.ok(apiResponse);
	}
	
	protected Exception buildErrorResponse(Throwable ex, String developerMessage) {
		return new Exception(developerMessage, ex);
	}
	
	protected ResponseEntity<APIResponse> okErrorMessage(String errorCode, String errorMessage) {
	    return ResponseEntity.ok(APIResponse.builder().errorMessage(errorMessage).errorCode(errorCode).build());
	}
	
	protected ResponseEntity<APIResponse> buildErrorResponse(String errorCode, String displayErrorMessage) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder().errorMessage(displayErrorMessage).errorCode(errorCode).build());
	}
	
	public void setTestExecutor(Executor testExecutor) {
		this.executor = testExecutor;
	}
}
