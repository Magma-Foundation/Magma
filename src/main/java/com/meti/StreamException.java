package com.meti;

public class StreamException extends Exception {
    public StreamException(Throwable cause) {
        super(cause);
    }

    public StreamException(String message) {
        super(message);
    }
}
