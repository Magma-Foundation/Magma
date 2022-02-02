package com.meti.app.compile.node.attribute;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;

public record NodesAttribute(List<Node> values) implements Attribute {
    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }

    @Override
    public Stream<Node> asStreamOfNodes() throws AttributeException {
        return values.stream();
    }

    public record Builder(List<Node> values) {
        public Builder() {
            this(List.createList());
        }

        public Builder add(Node next) {
            return new Builder(values.add(next));
        }

        public Attribute complete() {
            return new NodesAttribute(values);
        }
    }
}
