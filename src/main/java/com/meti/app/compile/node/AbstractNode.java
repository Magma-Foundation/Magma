package com.meti.app.compile.node;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractNode implements Node {
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
