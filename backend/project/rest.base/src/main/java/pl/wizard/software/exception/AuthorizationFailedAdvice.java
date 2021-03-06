package pl.wizard.software.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthorizationFailedAdvice {

    @ResponseBody
    @ExceptionHandler(AuthorizationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String authorizationFailedHandler(AuthorizationFailedException exception) {
        return exception.getMessage();
    }
}
