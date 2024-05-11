package com.meti.rule;

/**
 * Indicates a problem with rules, either lexing or rendering.
 */
public class RuleException extends RuntimeException {
    public RuleException(String message) {
        super(message);
    }

    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
