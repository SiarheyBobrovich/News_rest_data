package by.control.news.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(ConstraintViolationException e) {
        return ResponseEntity.badRequest()
                .body(e.getConstraintViolations()
                        .stream()
                        .map(x -> x.getPropertyPath().toString() + ": " + x.getMessage())
                        .reduce(new StringBuilder(), (StringBuilder::append), (s, s2) -> s.append("\n"))
                        .toString());
    }
}
