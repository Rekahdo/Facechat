package com.rekahdo.facechat.exceptions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.rekahdo.facechat.enums.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private interface SingleErrorView {};

    private interface MultiErrorView extends SingleErrorView {};

    @JsonView(ErrorResponse.SingleErrorView.class)
    private String exception;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private String line;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private int code;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private HttpStatus status;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private String message;

    @JsonView(ErrorResponse.MultiErrorView.class)
    private List<String> messages;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private int errorCount = 1;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private String description;

    @JsonView(ErrorResponse.SingleErrorView.class)
    private Instant timestamp = Instant.now();

    @JsonView(ErrorResponse.SingleErrorView.class)
    private StackTraceElement[] trace;

    @JsonIgnore
    private boolean isMultiErrors;

    public ErrorResponse(Exception ex, HttpStatusCode statusCode, WebRequest request) {
        this.exception = String.format("%s.class", ex.getClass().getSimpleName());
        this.line = fetchLine(ex);
        this.code = statusCode.value();;
        this.status = HttpStatus.valueOf(statusCode.value());;
        this.isMultiErrors = (ex instanceof BindException);
        this.message = (!isMultiErrors ? ex.getMessage() : null);
        this.messages = (isMultiErrors ? ((BindException) ex).getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).toList() : Collections.emptyList());
        this.errorCount = (isMultiErrors ? ((BindException) ex).getErrorCount() : 1);
        this.description = request.getDescription(true);
        this.trace = ex.getStackTrace();
    }

    public MappingJacksonValue fetchMJV() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(this);
        mappingJacksonValue.setSerializationView((isMultiErrors ?
                ErrorResponse.MultiErrorView.class : ErrorResponse.SingleErrorView.class));
        return mappingJacksonValue;
    }

    private String fetchLine(Exception ex){
        StackTraceElement[] stackTrace = ex.getStackTrace();
        return stackTrace.length > 0
                ? String.format("%s.%s:line-%d", stackTrace[0].getClassName(),
                stackTrace[0].getMethodName(), stackTrace[0].getLineNumber())
                : "Unknown";
    }

}