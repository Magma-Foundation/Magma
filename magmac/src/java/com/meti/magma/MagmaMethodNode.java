package com.meti.magma;

import com.meti.node.Renderable;

import java.util.Objects;

public final class MagmaMethodNode implements Renderable {
    private final String prefix;
    private final String name;
    private final String type;
    private final String content;
    private final String exceptionString;

    public MagmaMethodNode(String prefix, String name, String type, String content, String exceptionString) {
        this.prefix = prefix;
        this.name = name;
        this.type = type;
        this.content = content;
        this.exceptionString = exceptionString;
    }

    public String render() {
        return prefix() + "\n\tdef " + name() + "() : " + type() + exceptionString() + " => " + content();
    }

    public String prefix() {
        return prefix;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public String content() {
        return content;
    }

    public String exceptionString() {
        return exceptionString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MagmaMethodNode) obj;
        return Objects.equals(this.prefix, that.prefix) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.type, that.type) &&
               Objects.equals(this.content, that.content) &&
               Objects.equals(this.exceptionString, that.exceptionString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, name, type, content, exceptionString);
    }

    @Override
    public String toString() {
        return "MagmaMethodNode[" +
               "prefix=" + prefix + ", " +
               "name=" + name + ", " +
               "type=" + type + ", " +
               "content=" + content + ", " +
               "exceptionString=" + exceptionString + ']';
    }

}