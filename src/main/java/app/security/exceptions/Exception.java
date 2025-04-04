package app.security.exceptions;

import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException {
public HttpStatus status;
    public Exception(String message,HttpStatus status){
        super(message);
        this.status = status;
    }

}
