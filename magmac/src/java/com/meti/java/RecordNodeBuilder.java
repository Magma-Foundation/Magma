package com.meti.java;

import com.meti.node.Renderable;

public class RecordNodeBuilder implements RenderableBuilder {
    private String prefix;
    private String name;
    private String body;

    public RecordNodeBuilder() {
        this("", "", "");
    }

    public RecordNodeBuilder(String prefix, String name, String body) {
        this.prefix = prefix;
        this.name = name;
        this.body = body;
    }

    public RecordNodeBuilder withPrefix(String prefix) {
        return new RecordNodeBuilder(prefix, name, body);
    }

    public RecordNodeBuilder withName(String name) {
        return new RecordNodeBuilder(prefix, name, body);
    }

    public RecordNodeBuilder withBody(String body) {
        return new RecordNodeBuilder(prefix, name, body);
    }

    @Override
    public Renderable build() {
        return new RecordNode(prefix, name, body);
    }
}