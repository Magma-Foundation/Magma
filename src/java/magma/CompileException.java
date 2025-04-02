package magma;

public class CompileException extends Exception {
    public CompileException(String context, String message) {
        super(message + ": " + context);
    }
}
