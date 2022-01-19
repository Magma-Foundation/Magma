package com.meti.app.compile.stage;

public class ResolutionException extends CompileException {
    public ResolutionException(String message) {
        super(message);
    }

    public ResolutionException(Exception cause) {
        super(cause);
    }
}
