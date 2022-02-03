package com.meti.api.json;

import com.meti.app.compile.node.JSONable;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractJSONable implements JSONable {
    @Override
    public String toString() {
        try {
            return new JSONFormatter(toJSON()).toString();
        } catch (JSONException e) {
            var writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }
}
