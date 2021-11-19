package pl.wizard.software.sport.exception;

public class AuthorizationFailedException extends RuntimeException {

    public AuthorizationFailedException(String token) {
        super("Authorization token expired " + token);
    }
}
