package com.meti.app.compile.feature.function;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.Objects;

public final class Return extends AbstractNode {
    private final Node value;

    public Return(Node value) {
        this.value = value;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Streams.apply(Attribute.Type.Value)
                : Streams.empty();
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Return;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Return(attribute.asNode());
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode().addObject("returns", value.toJSON());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Return aReturn)) return false;
        return Objects.equals(value, aReturn.value);
    }
}
