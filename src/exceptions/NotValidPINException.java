package exceptions;

public class NotValidPINException extends Exception {
    public NotValidPINException() {
    }

    public NotValidPINException(String message) {
        super(message);
    }
}
