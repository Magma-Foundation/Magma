package com.meti.app.compile.cache;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;

public class JSONBuilder {
    private final List<Node> children;
    private final Node value;

    private JSONBuilder(List<Node> children, Node value) {
        this.children = children;
        this.value = value;
    }

    public static JSONBuilder createJSONBuilder(List<Node> children, Node value) {
        return new JSONBuilder(children, value);
    }

    String toString2() {
        return "{" +
               "\n\t\"value\":" + getValue() +
               ",\n\t\"buffer\":" + getChildren() +
               '}';
    }

    public Node getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }
}
