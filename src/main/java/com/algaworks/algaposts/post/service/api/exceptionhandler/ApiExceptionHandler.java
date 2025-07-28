package com.algaworks.algaposts.post.service.api.exceptionhandler;

import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Some fields are invalid. Please correct them and try again";

        // BindingResult armazena as violações de constraints de validação.

        // Utilizando stream, pega os FieldErrors e transforma em Field(minha classe).
        // Se for um Field, usamos o FieldError, se for uma classe usamos o ObjectError
        List<Field> problemFields = bindingResult.getAllErrors().stream().map(objectError -> {
            // Interface messageSource serve para usarmos mensagens padronizadas no
            // messages.properties
            String message = objectError.getDefaultMessage();

            String name = objectError.getObjectName();

            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
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
