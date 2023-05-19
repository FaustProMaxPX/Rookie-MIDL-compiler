package rookie.midl.semantic.exception;

public class DuplicateDefinitionException extends RuntimeException {
    public DuplicateDefinitionException(String message) {
        super(message);
    }
}
