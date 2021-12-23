package com.meti;

public class LexException extends CompileException {
    public LexException(String message) {
        super(message);
    }

    public LexException(Exception cause) {
        super(cause);
    }
}
