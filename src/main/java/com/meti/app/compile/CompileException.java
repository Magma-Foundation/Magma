package com.meti.app.compile;

import com.meti.app.ApplicationException;

public class CompileException extends ApplicationException {
    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(Throwable cause) {
        super(cause);
    }
}
