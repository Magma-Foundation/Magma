package com.meti.api.json;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.JSONable;

public record ArrayNode(List<String> children) implements JSONNode {
    public ArrayNode() {
        this(List.createList());
    }

    @Override
    public String toString() {
        try {
            return children.stream()
                    .foldRight((current, next) -> current + "," + next)
                    .map(value -> '[' + value + ']')
                    .orElse("[]");
        } catch (StreamException e) {
            return "";
        }
    }

    public record Builder(List<String> children) {
        public Builder() {
            this(List.createList());
        }

        public Builder addJSON(JSONable node) throws JSONException {
            return addObject(node.toJSON());
        }

        public Builder addObject(Object obj) {
            return new Builder(children.add(obj.toString()));
        }

        public Builder addString(String value) {
            return new Builder(children.add("\"" + value + "\""));
        }

        public JSONNode build() {
            return new ArrayNode(children);
        }
    }
}
