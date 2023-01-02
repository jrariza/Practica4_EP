package exceptions;

public class OutdatedCardException extends Exception {
    public OutdatedCardException() {
    }

    public OutdatedCardException(String message) {
        super(message);
    }
}
