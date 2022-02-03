package com.meti.app.compile.feature.util;

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

public final class Line extends AbstractNode {
    private final Node node;

    public Line(Node node) {
        this.node = node;
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode().addJSONable("child", node);
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return category == Attribute.Category.Value ? new Line(attribute.asNode()) : this;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        if (category == Attribute.Category.Value) return new NodeAttribute(node);
        throw new AttributeException(category);
    }

    @Override
    public Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node ? Streams.apply(Attribute.Category.Value) : Streams.empty();
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Line;
    }
}
