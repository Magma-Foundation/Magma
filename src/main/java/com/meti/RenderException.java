package com.meti;

public class RenderException extends CompileException {
    public RenderException(String message) {
        super(message);
    }

    public RenderException(Exception cause) {
        super(cause);
    }
}
