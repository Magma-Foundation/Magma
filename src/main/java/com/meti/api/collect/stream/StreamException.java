package com.meti.api.collect.stream;

import com.meti.api.collect.CollectionException;

public class StreamException extends CollectionException {
    public StreamException(String message) {
        super(message);
    }

    public StreamException(Exception cause) {
        super(cause);
    }
}
