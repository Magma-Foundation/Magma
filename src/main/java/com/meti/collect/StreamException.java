package com.meti.collect;

public class StreamException extends Exception {
    public StreamException(String message) {
        super(message);
    }

    public StreamException(Exception cause) {
        super(cause);
    }
}
