package com.meti.compile;

import com.meti.ApplicationException;

public class CompileException extends ApplicationException {
    public CompileException(Exception message) {
        super(message);
    }

    public CompileException(String message) {
        super(message);
    }
}
