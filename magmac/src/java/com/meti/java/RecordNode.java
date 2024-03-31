package com.meti.java;

import com.meti.node.Renderable;

import java.util.Objects;

public final class RecordNode implements com.meti.node.Renderable {
    private final String prefix;
    private final String name;
    private final String body;

    public RecordNode(String prefix, String name, String body) {
        this.prefix = prefix;
        this.name = name;
        this.body = body;
    }

    @Override
    public String render() {
        return prefix() + "record " + name() + "()" + body();
    }

    public String prefix() {
        return prefix;
    }

    public String name() {
        return name;
    }

    public String body() {
        return body;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RecordNode) obj;
        return Objects.equals(this.prefix, that.prefix) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, name, body);
    }

    @Override
    public String toString() {
        return "RecordNode[" +
               "prefix=" + prefix + ", " +
               "name=" + name + ", " +
               "body=" + body + ']';
    }

}