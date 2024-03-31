package com.meti.java;

public class JavaClassNodeBuilder implements RenderableBuilder {
    private final String prefix;
    private final String name;
    private final String content;

    public JavaClassNodeBuilder() {
        this("", "", "");
    }

    public JavaClassNodeBuilder(String prefix, String name, String content) {
        this.prefix = prefix;
        this.name = name;
        this.content = content;
    }

    public JavaClassNodeBuilder withPrefix(String prefix) {
        return new JavaClassNodeBuilder(prefix, name, content);
    }

    public JavaClassNodeBuilder withName(String name) {
        return new JavaClassNodeBuilder(prefix, name, content);
    }

    public JavaClassNodeBuilder withContent(String content) {
        return new JavaClassNodeBuilder(prefix, name, content);
    }

    public JavaClassNode build() {
        return new JavaClassNode(prefix, name, content);
    }

    public JavaClassNodeBuilder withContent(RenderableBuilder builder) {
        return withContent(builder.build().render());
    }
}