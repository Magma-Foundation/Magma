package com.meti.api.collect;

public class CollectionException extends Exception {
    public CollectionException(String message) {
        super(message);
    }

    public CollectionException(Throwable cause) {
        super(cause);
    }

    public CollectionException(String message, Exception cause) {
        super(message, cause);
    }
}
