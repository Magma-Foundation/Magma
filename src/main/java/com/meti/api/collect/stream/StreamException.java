package com.meti.api.collect.stream;

import com.meti.api.collect.CollectionException;

public class StreamException extends CollectionException {
    public StreamException(String message) {
        super(message);
    }

    public StreamException(String message, Exception cause) {
        super(message, cause);
    }

    public StreamException(Exception cause) {
        super(cause);
    }
}
