package exceptions;

public class IncorrectVerificationException extends Exception{

    public IncorrectVerificationException() {
    }

    public IncorrectVerificationException(String message) {
        super(message);
    }
}
