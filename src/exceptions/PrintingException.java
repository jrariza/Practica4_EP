package exceptions;

public class PrintingException extends Exception {
    public PrintingException() {
    }

    public PrintingException(String message) {
        super(message);
    }
}
