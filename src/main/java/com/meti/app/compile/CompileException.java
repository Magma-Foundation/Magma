package com.meti.app.compile;

public class CompileException extends Exception {
    public CompileException(Throwable cause) {
        super(cause);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(String message) {
        super(message);
    }
}
