package com.meti;

public class MagmaClassNodeBuilder {
    private String prefix;
    private String name;
    private String content;

    public MagmaClassNodeBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public MagmaClassNodeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MagmaClassNodeBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MagmaClassNode createMagmaClassNode() {
        return new MagmaClassNode(prefix, name, content);
    }
}