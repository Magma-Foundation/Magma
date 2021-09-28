package com.meti;

public record Content(String value) implements Node {
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Group group() {
        return Group.Content;
    }

    @Override
    public String render() {
        throw new UnsupportedOperationException("Unparsed content cannot be rendered.");
    }
}
