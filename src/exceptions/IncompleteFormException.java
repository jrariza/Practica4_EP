package exceptions;

public class IncompleteFormException extends Exception {
    public IncompleteFormException() {
    }

    public IncompleteFormException(String message) {
        super(message);
    }
}
