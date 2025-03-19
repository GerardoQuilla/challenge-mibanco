package com.jgerardo.challenge.shared.infrastructure;

import com.jgerardo.challenge.expense.domain.exceptions.ExpenseNotFoundException;
import com.jgerardo.challenge.shared.interfaces.rest.resources.MessageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<MessageResource> handle(ExpenseNotFoundException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResource(e.getMessage()));

    }
}
