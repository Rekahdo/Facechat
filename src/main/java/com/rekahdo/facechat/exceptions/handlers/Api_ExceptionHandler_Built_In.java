package com.rekahdo.facechat.exceptions.handlers;
import com.rekahdo.facechat.exceptions.classes.*;
import com.rekahdo.facechat.exceptions.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.rekahdo.facechat.enums.Error.*;

@RestControllerAdvice
public class Api_ExceptionHandler_Built_In {

    // IN-BUILT DEFINED EXCEPTIONS
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(USERNAME_NOT_FOUND_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(USER_NOT_AUTHORIZED_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(USER_NOT_AUTHORIZED_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(USER_NOT_AUTHORIZED_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(SQL_INTEGRITY_CONSTRAINT_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(NULL_VALUE_IN_FIELD_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(NULL_VALUE_IN_FIELD_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<?> handleJpaSystemException(JpaSystemException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ORPHANED_COLLECTION_REFERENCE_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ORPHANED_COLLECTION_REFERENCE_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<?> handleHttpMessageConversionException(HttpMessageConversionException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(UNKNOWN_VIOLATION, ex, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.fetchMJV());
    }

}