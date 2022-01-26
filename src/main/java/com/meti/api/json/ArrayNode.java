package com.meti.api.json;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;

public record ArrayNode(List<JSONNode> children) implements JSONNode {
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

    public String toFormat() {
        var value = toString();
        return value;
    }

    public record Builder(List<JSONNode> children) {
        public Builder() {
            this(List.createList());
        }

        public Builder add(JSONNode node) {
            return new Builder(children.add(node));
        }

        public JSONNode builder() {
            return new ArrayNode(children);
        }
    }
}
