package exceptions;

public class NotValidPaymentImportException extends Exception {
    public NotValidPaymentImportException() {
    }

    public NotValidPaymentImportException(String message) {
        super(message);
    }
}
