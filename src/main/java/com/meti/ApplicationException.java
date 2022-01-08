package com.meti;

public class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Exception cause) {
        super(cause);
    }
}
