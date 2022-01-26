package com.meti.api.json;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;

public class ArrayNode implements JSONNode {
    private final List<JSONNode> children;

    public ArrayNode(List<JSONNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        try {
            return children.stream()
                    .map(JSONNode::toString)
                    .foldRight((current, next) -> current + "," + next)
                    .map(value -> '[' + value + ']')
                    .orElse("[]");
        } catch (StreamException e) {
            return "";
        }
    }

    @Override
    public String toFormat() {
        var value = toString();
        return value;
    }
}
