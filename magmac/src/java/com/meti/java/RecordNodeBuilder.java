package com.meti.java;

public class RecordNodeBuilder {
    private String prefix;
    private String name;
    private String body;

    public RecordNodeBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public RecordNodeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RecordNodeBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public RecordNode createRecordNode() {
        return new RecordNode(prefix, name, body);
    }
}