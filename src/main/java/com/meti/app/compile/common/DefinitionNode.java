package com.meti.app.compile.common;

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

public final class DefinitionNode extends AbstractNode {
    private final Node identity;

    public DefinitionNode(Node identity) {
        this.identity = identity;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Definition ? Streams.apply(Attribute.Type.Identity) : Streams.empty();
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Declaration;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Identity) return new NodeAttribute(identity);
        throw new AttributeException(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Identity ? new DefinitionNode(attribute.asNode()) : this;
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode().addObject("identity", identity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefinitionNode)) return false;
        DefinitionNode that = (DefinitionNode) o;
        return Objects.equals(identity, that.identity);
    }
}
