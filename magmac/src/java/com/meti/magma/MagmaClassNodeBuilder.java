package com.meti.magma;

import com.meti.java.RenderableBuilder;

public class MagmaClassNodeBuilder implements RenderableBuilder {
    private String prefix;
    private String name;
    private String content;

    public MagmaClassNodeBuilder() {
        this("", "", "");
    }

    public MagmaClassNodeBuilder(String prefix, String name, String content) {
        this.prefix = prefix;
        this.name = name;
        this.content = content;
    }

    public MagmaClassNodeBuilder withPrefix(String prefix) {
        return new MagmaClassNodeBuilder(prefix, name, content);
    }

    public MagmaClassNodeBuilder withName(String name) {
        return new MagmaClassNodeBuilder(prefix, name, content);
    }

    public MagmaClassNodeBuilder withContent(String content) {
        return new MagmaClassNodeBuilder(prefix, name, content);
    }

    @Override
    public MagmaClassNode build() {
        return new MagmaClassNode(prefix, name, content);
    }
}