package com.meti.compile;

import com.meti.ApplicationException;
import com.meti.collect.StreamException;

public class CompileException extends ApplicationException {
    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Exception cause) {
        super(message, cause);
    }

    public CompileException(Exception cause) {
        super(cause);
    }
}
