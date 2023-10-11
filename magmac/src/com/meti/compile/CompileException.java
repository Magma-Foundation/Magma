package com.meti.compile;

import com.meti.compile.rule.RuleException;

public class CompileException extends Exception {
    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(Exception cause) {
        super(cause);
    }
}
