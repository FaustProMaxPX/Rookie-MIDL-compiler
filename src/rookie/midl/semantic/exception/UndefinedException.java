package rookie.midl.semantic.exception;

/**
 * this exception will be thrown when user use an element without defining it
 * */
public class UndefinedException extends RuntimeException {

    public UndefinedException(String message) {
        super(message);
    }

}
