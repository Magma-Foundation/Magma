package magma.compile;

import java.io.IOException;

public class CompileException extends Exception {
    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(String message) {
        super(message);
    }

    public CompileException() {
    }

    public CompileException(IOException cause) {
        super(cause);
    }
}
