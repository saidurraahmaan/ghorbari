package com.s4r.ghorbari.web.exception;

import com.s4r.ghorbari.core.exception.ErrorCode;
import com.s4r.ghorbari.core.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(
            ServiceException ex,
            HttpServletRequest request) {
        logger.error("Service exception occurred: {} - {}", ex.getCode(), ex.getMessage());

        HttpStatus httpStatus = mapErrorCodeToHttpStatus(ex.getErrorCode());

        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.getReasonPhrase().toUpperCase().replace(" ", "_"),
                ex.getCode(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private HttpStatus mapErrorCodeToHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            // NOT_FOUND errors
            case USER_NOT_FOUND, ROLE_NOT_FOUND, DEFAULT_ROLE_NOT_FOUND,
                 TENANT_NOT_FOUND, RESOURCE_NOT_FOUND -> HttpStatus.NOT_FOUND;

            // CONFLICT errors
            case USER_ALREADY_EXISTS, ROLE_ALREADY_EXISTS, TENANT_ALREADY_EXISTS,
                 RESOURCE_ALREADY_EXISTS, DATA_INTEGRITY_VIOLATION, CONSTRAINT_VIOLATION -> HttpStatus.CONFLICT;

            // FORBIDDEN errors
            case OPERATION_NOT_ALLOWED -> HttpStatus.FORBIDDEN;

            // BAD_REQUEST for everything else
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex,
            HttpServletRequest request) {
        logger.error("Unauthorized: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "UNAUTHORIZED",
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {
        logger.error("Authentication failed: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "UNAUTHORIZED",
                "Authentication failed"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        logger.error("Validation failed: {}", ex.getMessage());

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ValidationError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_FAILED",
                "Validation failed for request",
                validationErrors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {
        logger.error("Constraint violation: {}", ex.getMessage());

        List<ErrorResponse.ValidationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse.ValidationError(
                        violation.getPropertyPath().toString(),
                        violation.getInvalidValue(),
                        violation.getMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_FAILED",
                "Constraint violation",
                validationErrors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {
        logger.error("Unexpected error occurred: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred. Please try again later."
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
