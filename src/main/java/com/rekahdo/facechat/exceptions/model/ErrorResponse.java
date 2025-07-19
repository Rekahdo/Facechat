package com.rekahdo.facechat.exceptions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.rekahdo.facechat.enums.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

    @JsonView(SingleErrorView.class)
    private String exception;

    @JsonView(SingleErrorView.class)
    private Error error;

    @JsonView(SingleErrorView.class)
    private String message;

    @JsonView(MultiErrorView.class)
    private List<String> messages;

    @JsonView(SingleErrorView.class)
    private int errorCount = 1;

    @JsonView(SingleErrorView.class)
    private String description;

    @JsonView(SingleErrorView.class)
    private String status;

    @JsonView(SingleErrorView.class)
    private Instant timestamp = Instant.now();

    @JsonIgnore
    private boolean isMultiErrors;

    public ErrorResponse(Error statusValue, Exception ex, WebRequest request) {
        this.exception = String.format("%s.class", ex.getClass().getSimpleName());
        this.status = null;
        this.isMultiErrors = (ex instanceof BindException);
        this.error = statusValue;
        this.message = (!isMultiErrors ? ex.getMessage() : null);
        this.messages = (isMultiErrors ? ((BindException) ex).getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).toList() : Collections.emptyList());
        this.errorCount = (isMultiErrors ? ((BindException) ex).getErrorCount() : 1);
        this.description = request.getDescription(true);
    }

    public MappingJacksonValue fetchMJV() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(this);
        mappingJacksonValue.setSerializationView((isMultiErrors ?
                MultiErrorView.class : SingleErrorView.class));
        return mappingJacksonValue;
    }

}