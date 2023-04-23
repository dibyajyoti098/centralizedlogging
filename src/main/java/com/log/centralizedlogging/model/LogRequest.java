package com.log.centralizedlogging.model;

import java.util.Date;

public class LogRequest {

    private String applicationId;
    private String traceId;
    private String componentName;
    private int requestId;
    private String severity;
    private Date timestamp;
    private String message;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    @Override
    public String toString() {
        return "LogRequest{" +
                "applicationId='" + applicationId + '\'' +
                ", traceId='" + traceId + '\'' +
                ", componentName='" + componentName + '\'' +
                ", requestId='" + requestId + '\'' +
                ", severity='" + severity + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
