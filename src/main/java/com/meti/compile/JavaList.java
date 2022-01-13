package com.meti.compile;

import com.meti.compile.node.Node;

import java.util.List;

public class JavaList {
    private final List<Node> value;

    public JavaList(List<Node> value) {
        this.value = value;
    }

    JavaList insert(int index, Node value) {
        getValue().add(index, value);
        return new JavaList(getValue());
    }

    public List<Node> getValue() {
        return value;
    }

    int size() {
        return getValue().size();
    }
}
