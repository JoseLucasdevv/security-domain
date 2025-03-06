package app.security.exceptions;


import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, handleErrorResponse>> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, handleErrorResponse> errors = new HashMap<>();
            ex.getConstraintViolations().forEach(err ->{
                errors.put("error",new handleErrorResponse(err.getPropertyPath().toString(),err.getMessage()));

            });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, handleErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, handleErrorResponse> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            errors.put("error",new handleErrorResponse(((FieldError) err).getField(),err.getDefaultMessage()));
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, handleErrorResponse>> handleValidationExceptions(BadCredentialsException ex) {
        Map<String, handleErrorResponse> errors = new HashMap<>();

        errors.put("error",new handleErrorResponse("username or password",ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleBadRequest(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}