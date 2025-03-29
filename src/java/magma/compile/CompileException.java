package magma.compile;

public class CompileException extends Throwable {
    public CompileException(String message, String context) {
        super(message + ": " + context);
    }
}
