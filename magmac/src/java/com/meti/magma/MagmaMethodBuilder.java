package com.meti.magma;

public class MagmaMethodBuilder {
    private String prefix;
    private String name;
    private String type;
    private String content;
    private String exceptionString;

    public MagmaMethodBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public MagmaMethodBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MagmaMethodBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public MagmaMethodBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public MagmaMethodBuilder withExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
        return this;
    }

    public MagmaMethodNode build() {
        return new MagmaMethodNode(prefix, name, type, content, exceptionString);
    }
}