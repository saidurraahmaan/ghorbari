package com.s4r.ghorbari.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Standard error response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @Schema(description = "Error type/code", example = "BAD_REQUEST")
    private String error;

    @Schema(description = "Application error code", example = "1001")
    private Integer errorCode;

    @Schema(description = "Error message", example = "Validation failed")
    private String message;

    @Schema(description = "Detailed validation errors")
    private List<ValidationError> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String error, Integer errorCode, String message) {
        this.error = error;
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorResponse(String error, String message, List<ValidationError> validationErrors) {
        this.error = error;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Schema(description = "Validation error detail")
    public static class ValidationError {

        @Schema(description = "Field name that failed validation", example = "email")
        private String field;

        @Schema(description = "Rejected value")
        private Object rejectedValue;

        @Schema(description = "Validation error message", example = "must be a valid email address")
        private String message;

        public ValidationError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
