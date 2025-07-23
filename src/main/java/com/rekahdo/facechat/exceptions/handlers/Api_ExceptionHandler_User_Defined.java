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
public class Api_ExceptionHandler_User_Defined {

    // API DEFINED EXCEPTIONS
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<?> handleUserIdNotFoundException(UserIdNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<?> handleUsernameExistException(UsernameExistException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<?> handleEmptyListException(EmptyListException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(OnlyReceiverException.class)
    public ResponseEntity<?> handleOnlyReceiverException(OnlyReceiverException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(ModificationException.class)
    public ResponseEntity<?> handleModificationException(ModificationException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(FriendshipNotFoundException.class)
    public ResponseEntity<?> handleFriendshipNotFoundException(FriendshipNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<?> handleChatNotFoundException(ChatNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusValue(), ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse.fetchMJV());
    }

}