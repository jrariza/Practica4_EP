package exceptions;

public class IncorrectValDateException extends Exception {
    public IncorrectValDateException() {
    }

    public IncorrectValDateException(String message) {
        super(message);
    }
}
