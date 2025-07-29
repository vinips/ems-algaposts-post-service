package com.algaworks.algaposts.post.service.api.exceptionhandler;

import com.algaworks.algaposts.post.service.domain.exception.EntityNotFoundException;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(),
                headers, HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem problem = createProblemBuilder(status,
                ProblemType.ENTITY_NOT_FOUND, ex.getMessage(), ex.getMessage());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Some fields are invalid. Please correct them and try again";

        List<Field> problemFields = bindingResult.getAllErrors().stream().map(
                objectError -> {
                    String message = objectError.getDefaultMessage();
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError fieldError) {
                        name = fieldError.getField();
                    }

                    return new Field(name, message);
                }).toList();

        Problem problem = createProblemBuilder(status, ProblemType.BAD_REQUEST, detail, detail);
        problem.setFields(problemFields);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    public Problem createProblemBuilder(HttpStatus status, ProblemType problemType,
                                        String detail, String userMessage) {

        return Problem.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(userMessage)
                .build();
    }


}
