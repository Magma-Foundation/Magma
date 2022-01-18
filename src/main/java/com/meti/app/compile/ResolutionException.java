package com.meti.app.compile;

public class ResolutionException extends CompileException {
    public ResolutionException(String message) {
        super(message);
    }

    public ResolutionException(Exception cause) {
        super(cause);
    }
}
