package com.husqvarna.todoapp.exception;

import com.husqvarna.todoapp.constant.TodoApiConstants;
import com.husqvarna.todoapp.generated.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class is used as an Application Global handler for handling custom and runtime exceptions
 */
@ControllerAdvice
public class TodoExceptionHandler {

    /**
     * Method to handle custom api exception
     *
     * @param todoAPIException custom exception object
     * @return Response Entity with custom message and appropriate Http status
     */
    @ExceptionHandler({TodoAPIException.class})
    public ResponseEntity<ErrorResponse> handleTodoApiException(TodoAPIException todoAPIException) {
        ErrorResponse errorResponse = new ErrorResponse(todoAPIException.getErrorCode(), todoAPIException.getErrorMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    /**
     * Method to handle runtime exception
     *
     * @param exception runtime exception object
     * @return Response Entity with message and appropriate Http status
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.internalServerError().
                body(exception.getMessage());
    }

    /**
     * Method to handle exception when required params are not provided or value is not as per the contract
     * @return error details with custom error code and message
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException() {
        ErrorResponse errorResponse = new ErrorResponse(TodoApiConstants.MISSING_OR_INVALID_PARAMETERS_ERROR_CODE, TodoApiConstants.MISSING_OR_INVALID_PARAMETERS_ERROR_MESSAGE);
          return ResponseEntity.badRequest()
                .body(errorResponse);
    }

}
