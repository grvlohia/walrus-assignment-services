package com.walrus.assignment.undeterred.models;

public class RetryUpdate {
    private int retryCount;
    private long waitTime;
    private String responseBody;
    private int responseStatus;

    public RetryUpdate() {
    }

    public RetryUpdate(int retryCount, long waitTime, String responseBody, int responseStatus) {
        this.retryCount = retryCount;
        this.waitTime = waitTime;
        this.responseBody = responseBody;
        this.responseStatus = responseStatus;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getWaitTime() {
        return this.waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getResponseStatus() {
        return this.responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}
