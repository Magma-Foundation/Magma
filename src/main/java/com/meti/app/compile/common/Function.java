package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.JSONException;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;

import java.util.Objects;

public abstract class Function extends AbstractNode {
    protected final Node identity;
    protected final List<Node> parameters;

    public Function(Node identity, List<Node> parameters) {
        this.identity = identity;
        this.parameters = parameters;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        return switch (category) {
            case Identity -> new NodeAttribute(identity);
            case Parameters -> new NodesAttribute(parameters);
            default -> throw new AttributeException(category);
        };
    }

    @Override
    public Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Definitions -> Streams.apply(Attribute.Category.Parameters);
            case Definition -> Streams.apply(Attribute.Category.Identity);
            default -> Streams.empty();
        };
    }

    @Override
    public ObjectNode toJSON() throws JSONException {
        ObjectNode objectNode;
        try {
            var jsonParameters = parameters.stream()
                    .map(Node::toJSON)
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addObject)
                    .build();

            objectNode = new ObjectNode()
                    .addObject("identity", identity.toJSON())
                    .addObject("parameters", jsonParameters);
        } catch (StreamException e) {
            objectNode = new ObjectNode();
        }
        return objectNode;
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        try {
            return switch (category) {
                case Identity -> complete(attribute.asNode(), parameters);
                case Parameters -> complete(identity, attribute.asStreamOfNodes()
                        .foldRight(List.createList(), List::add));
                default -> throw new AttributeException(category);
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    protected abstract Node complete(Node node, List<Node> parameters);

    @Override
    public int hashCode() {
        return Objects.hash(identity, parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function function)) return false;
        return Objects.equals(identity, function.identity) && Objects.equals(parameters, function.parameters);
    }
}
