package uz.com.employee_position.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {super(message);
    }
}
