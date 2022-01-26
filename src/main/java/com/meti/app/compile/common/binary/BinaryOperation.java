package com.meti.app.compile.common.binary;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute1;

public record BinaryOperation(Node operator, Node first, Node second) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Streams.apply(Attribute.Type.Operator);
            case Nodes -> Streams.apply(Attribute.Type.Arguments);
            default -> Streams.empty();
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Binary;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Operator -> new NodeAttribute(operator);
            case Arguments -> new NodesAttribute1(List.apply(first, second));
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Operator -> new BinaryOperation(attribute.asNode(), first, second);
                case Arguments -> {
                    var arguments = attribute.asStreamOfNodes1()
                            .foldRight(List.<Node>createList(), List::add);
                    yield new BinaryOperation(operator, arguments.apply(0), arguments.apply(1));
                }
                default -> this;
            };
        } catch (StreamException e) {
            return this;
        }
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode()
                .addJSON("operator", operator)
                .addJSON("first", first)
                .addJSON("second", second);
    }
}
