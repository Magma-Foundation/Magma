package com.meti;

public class EmptyNode implements Node {
    public static final Node EmptyNode_ = new EmptyNode();

    public EmptyNode() {
    }

    @Override
    public Group group() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render() {
        return "";
    }
}
