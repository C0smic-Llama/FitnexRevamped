package com.project.platform;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail notFound(ResourceNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail invalid(MethodArgumentNotValidException ex){
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Validation Failed");
        pd.setProperties(ex.getBindingResult().
                getFieldErrors().
                stream().
                collect(
                    Collectors.toMap(
                        FieldError::getField,
                        fe ->  String.valueOf(fe.getDefaultMessage()),
                        (a,b)->a)));

        return pd;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail conflict(DataIntegrityViolationException ex){
        String root = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();

        if(root.contains("uq_members_email"))
            return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"A user with this email already exists bruh");

        if(root.contains("uq_members_cognito_sub"))
            return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"User already exists");

        log.error("Unhandled conflict error",ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Unhandled conflict error, check it out");
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail unhandled(Exception ex){
        log.error("Unhandled error, handle it :(");
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected error");
    }
}
