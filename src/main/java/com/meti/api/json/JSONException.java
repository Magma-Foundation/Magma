package com.meti.api.json;

import com.meti.api.collect.stream.StreamException;

public class JSONException extends Exception {
    public JSONException(String message) {
        super(message);
    }

    public JSONException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONException(StreamException cause) {
        super(cause);
    }
}
