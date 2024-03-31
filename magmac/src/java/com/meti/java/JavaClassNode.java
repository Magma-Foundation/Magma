package com.meti.java;

import com.meti.Lang;
import com.meti.node.Renderable;

import java.util.Objects;

public final class JavaClassNode implements Renderable {
    private final String prefix;
    private final String name;
    private final String content;

    public JavaClassNode(String prefix, String name, String content) {
        this.prefix = prefix;
        this.name = name;
        this.content = content;
    }

    public String render() {
        return prefix() + Lang.CLASS_KEYWORD + name() + " {" + content() + "}";
    }

    public String prefix() {
        return prefix;
    }

    public String name() {
        return name;
    }

    public String content() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (JavaClassNode) obj;
        return Objects.equals(this.prefix, that.prefix) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, name, content);
    }

    @Override
    public String toString() {
        return "JavaClassNode[" +
               "prefix=" + prefix + ", " +
               "name=" + name + ", " +
               "content=" + content + ']';
    }

}