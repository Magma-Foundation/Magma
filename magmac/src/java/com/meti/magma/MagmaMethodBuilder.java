package com.meti.magma;

import com.meti.java.RenderableBuilder;

public class MagmaMethodBuilder implements RenderableBuilder {
    private String prefix;
    private String name;
    private String type;
    private String content;
    private String exceptionString;

    public MagmaMethodBuilder() {
        this("", "", "", "", "");
    }

    public MagmaMethodBuilder(String prefix, String name, String type, String content, String exceptionString) {
        this.prefix = prefix;
        this.name = name;
        this.type = type;
        this.content = content;
        this.exceptionString = exceptionString;
    }

    public MagmaMethodBuilder withPrefix(String prefix) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString);
    }

    public MagmaMethodBuilder withName(String name) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString);
    }

    public MagmaMethodBuilder withType(String type) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString);
    }

    public MagmaMethodBuilder withContent(String content) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString);
    }

    public MagmaMethodBuilder withExceptionString(String exceptionString) {
        return new MagmaMethodBuilder(prefix, name, type, content, exceptionString);
    }

    public MagmaMethodNode build() {
        return new MagmaMethodNode(prefix, name, type, content, exceptionString);
    }
}