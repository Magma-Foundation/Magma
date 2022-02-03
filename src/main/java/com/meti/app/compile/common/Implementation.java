package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.JSONException;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.Objects;

public final class Implementation extends Function {
    private final Node body;

    public Implementation(Node identity, Node body, Node... parameters) {
        this(identity, body, List.apply(parameters));
    }

    public Implementation(Node identity, Node body, List<Node> parameters) {
        super(identity, parameters);
        this.body = body;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        return category == Attribute.Category.Value ? new NodeAttribute(body) : super.apply(category);
    }

    @Override
    public Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        if (group == Attribute.Group.Node) return Streams.apply(Attribute.Category.Value);
        else return super.apply(group);
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return category == Attribute.Category.Value ? new Implementation(identity, attribute.asNode(), parameters) : super.with(category, attribute);
    }

    @Override
    protected Node complete(Node node, List<Node> parameters) {
        return new Implementation(node, body, parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Implementation that = (Implementation) o;
        return super.equals(o) && Objects.equals(body, that.body);
    }

    @Override
    public ObjectNode toJSON() throws JSONException {
        return super.toJSON().addObject("body", body.toJSON());
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Implementation;
    }
}
