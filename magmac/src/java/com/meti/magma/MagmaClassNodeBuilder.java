package com.meti.magma;

public class MagmaClassNodeBuilder {
    private String prefix;
    private String name;
    private String content;

    public MagmaClassNodeBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public MagmaClassNodeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MagmaClassNodeBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public MagmaClassNode build() {
        return new MagmaClassNode(prefix, name, content);
    }
}