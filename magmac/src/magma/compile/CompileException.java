package magma.compile;

public class CompileException extends Exception {
    public final String content;

    public CompileException(String message) {
        super(message);
        this.content = "";
    }

    public CompileException(String message, String content) {
        super(message);
        this.content = content;
    }

    public CompileException(String message, String content, Throwable cause) {
        super(message, cause);
        this.content = content;
    }

    public int calculateDepth() {
        var cause = getCause();
        if (cause instanceof CompileException compileException) {
            return 1 + compileException.calculateDepth();
        }
        return 1;
    }
}
