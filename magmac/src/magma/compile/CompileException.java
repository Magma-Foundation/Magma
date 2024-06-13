package magma.compile;

public class CompileException extends Exception {
    public CompileException(String message) {
        super(message);
    }

    public int calculateDepth() {
        var cause = getCause();
        if (cause instanceof CompileException compileException) {
            return 1 + compileException.calculateDepth();
        }
        return 1;
    }
}
