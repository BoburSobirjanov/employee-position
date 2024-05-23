package uz.com.employee_position.exception;

public class DataHasAlreadyExistException extends RuntimeException {
    public DataHasAlreadyExistException(String message) {
        super(message);
    }
}
