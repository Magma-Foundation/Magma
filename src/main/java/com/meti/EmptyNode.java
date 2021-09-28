package com.meti;

public class EmptyNode implements Node {
    public static final Node EmptyNode_ = new EmptyNode();

    public EmptyNode() {
    }

    @Override
    public String render() {
        return "";
    }
}
