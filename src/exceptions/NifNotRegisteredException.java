package exceptions;

public class NifNotRegisteredException extends Exception {
    public NifNotRegisteredException() {
    }

    public NifNotRegisteredException(String message) {
        super(message);
    }
}
