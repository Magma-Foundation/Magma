package com.meti;

public class MagmaMethodBuilder {
    private String prefix;
    private String name;
    private String type;
    private String content;
    private String exceptionString;

    public MagmaMethodBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public MagmaMethodBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MagmaMethodBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public MagmaMethodBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MagmaMethodBuilder setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
        return this;
    }

    public MagmaMethodNode createMagmaMethodNode() {
        return new MagmaMethodNode(prefix, name, type, content, exceptionString);
    }
}