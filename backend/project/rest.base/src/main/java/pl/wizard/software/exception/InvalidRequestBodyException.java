package pl.wizard.software.exception;

public class InvalidRequestBodyException extends RuntimeException {
    public InvalidRequestBodyException(String s) {
        super(s);
    }
}
