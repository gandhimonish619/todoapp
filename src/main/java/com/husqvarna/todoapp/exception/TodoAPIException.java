package com.husqvarna.todoapp.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception class for handling service layer exceptions
 */
@Getter
@Setter
public class TodoAPIException extends RuntimeException{
    private final String errorCode;
    private final String traceId;
    private final String errorMessage;

    /**
     * TodoAPIException constructor to populate exception details
     * @param errorCode error code to be returned by the service
     * @param traceId trace id of the request
     * @param errorMessage error message to be returned
     */
    public TodoAPIException(String errorCode, String errorMessage, String traceId)
    {
        this.errorCode = errorCode;
        this.traceId = traceId;
        this.errorMessage = errorMessage;
    }
}
