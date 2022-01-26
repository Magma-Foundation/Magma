package com.meti.app.compile.node;

import com.meti.api.json.JSONFormatter;

public abstract class AbstractNode implements Node {
    @Override
    public String toString() {
        return new JSONFormatter(toJSON()).toString();
    }
}
