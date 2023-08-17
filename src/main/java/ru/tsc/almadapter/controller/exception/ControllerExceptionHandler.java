package ru.tsc.almadapter.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.tsc.almadapter.model.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage generalExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage("данные не загружены", ex.getMessage());
    }

    // Если у вас есть другие конкретные исключения для обработки, добавьте их обработчики здесь
}
