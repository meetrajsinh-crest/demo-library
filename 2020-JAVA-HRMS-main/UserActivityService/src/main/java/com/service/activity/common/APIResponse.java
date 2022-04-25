package com.service.activity.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
public class APIResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SERVER_TIMESTAMP_KEY = "serverTimeStamp";
	public static final String ROW_COUNT_KEY = "rowCount";
	public static final String ERROR_CODE_KEY = "code";
    public static final String INTERNAL_ERROR_KEY = "internalError";
    public static final String ADDITIONAL_INFO_KEY = "additionalInfo";
	public static final String DISPLAY_MESSAGE_KEY = "displayMessage";
	
	private Map<String, Object> meta = new HashMap<String, Object>();

	private Object data;
	
	private Map<String, Object> error = new HashMap<String, Object>();
	
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Map<String, Object> getError() {
		return error;
	}
	public void setError(Map<String, Object> error) {
		this.error = error;
	}
	public APIResponse() {
        meta.put(SERVER_TIMESTAMP_KEY, Calendar.getInstance().getTime());
    }
    public static APIResponseBuilder builder() {
        return new APIResponseBuilder();
    }
    public static class APIResponseBuilder {

        private APIResponse apiResponse = new APIResponse();
                                
        public APIResponseBuilder data(Object data) {

            if ((data != null) && (data instanceof Collection)) {
                metaData(ROW_COUNT_KEY, ((Collection<?>) data).size());
            }
            apiResponse.setData(data);
            return this;
        }

        public APIResponseBuilder metaData(String key, Object value) {
            apiResponse.meta.put(key, value);
            return this;
        }

        public APIResponseBuilder errorCode(String errorCode) {
            apiResponse.error.put(ERROR_CODE_KEY, errorCode);
            return this;
        }

        public APIResponseBuilder internalError(String internalError) {
            apiResponse.error.put(INTERNAL_ERROR_KEY, internalError);
            return this;
        }

        public APIResponseBuilder additionalErrorInfo(Object additionalInfo) {
            apiResponse.error.put(ADDITIONAL_INFO_KEY, additionalInfo);
            return this;
        }

        public APIResponseBuilder errorMessage(String errorMessage) {
			apiResponse.error.put(DISPLAY_MESSAGE_KEY, errorMessage);
            return this;
        }

        public APIResponse build() {
            return apiResponse;
        }
    }

}
