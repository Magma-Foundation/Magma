package com.meti.java;

public class JavaClassNodeBuilder {
    private String prefix;
    private String name;
    private String content;

    public JavaClassNodeBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public JavaClassNodeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public JavaClassNodeBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public JavaClassNode createJavaClassNode() {
        return new JavaClassNode(prefix, name, content);
    }
}