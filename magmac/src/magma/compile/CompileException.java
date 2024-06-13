package magma.compile;

public class CompileException extends Exception {
    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public int calculateDepth() {
        var cause = getCause();
        if (cause instanceof CompileException compileException) {
            return 1 + compileException.calculateDepth();
        }
        return 1;
    }
}
