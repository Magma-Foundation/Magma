package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONFormatter;

public abstract class AbstractAttribute implements Attribute {
    @Override
    public String toString() {
        try {
            return new JSONFormatter(toJSON()).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
