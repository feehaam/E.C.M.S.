package com.ecm.productscommand.exceptions;

import java.util.Date;

public class ErrorResponse {
    private String exception;
    private String message;
    private String status;
    private Date timeStamp;
    private String apiPath;

    public ErrorResponse(String exception, String message, String status, Date timeStamp, String apiPath) {
        this.exception = exception;
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
        this.apiPath = apiPath;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }
}
