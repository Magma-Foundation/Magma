package com.meti;

public class CompileException extends ApplicationException {
    public CompileException(Exception message) {
        super(message);
    }

    public CompileException(String message) {
        super(message);
    }
}
