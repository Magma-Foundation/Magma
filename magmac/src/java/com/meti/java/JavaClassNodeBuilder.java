package com.meti.java;

public class JavaClassNodeBuilder {
    private String prefix;
    private String name;
    private String content;

    public JavaClassNodeBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public JavaClassNodeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public JavaClassNodeBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public JavaClassNode build() {
        return new JavaClassNode(prefix, name, content);
    }
}