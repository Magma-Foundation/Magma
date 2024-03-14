package com.meti.compile;

public class CompileException extends Exception {
    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(String message) {
        super(message);
    }
}
