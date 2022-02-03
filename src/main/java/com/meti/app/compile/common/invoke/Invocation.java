package com.meti.app.compile.common.invoke;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;

import java.util.Objects;

public final class Invocation extends AbstractNode {
    private final Node caller;
    private final List<Node> arguments;

    public Invocation(Node caller, List<Node> arguments) {
        this.caller = caller;
        this.arguments = arguments;
    }

    public Invocation(Node caller, Node... arguments) {
        this.caller = caller;
        this.arguments = List.apply(arguments);
    }

    @Override
    public Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Streams.apply(Attribute.Category.Caller);
            case Nodes -> Streams.apply(Attribute.Category.Arguments);
            default -> Streams.empty();
        };
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Invocation;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        return switch (category) {
            case Caller -> new NodeAttribute(caller);
            case Arguments -> new NodesAttribute(arguments);
            default -> throw new AttributeException(category);
        };
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        try {
            return switch (category) {
                case Caller -> new Invocation(attribute.asNode(), arguments);
                case Arguments -> new Invocation(caller, attribute
                        .asStreamOfNodes()
                        .foldRight(List.createList(), List::add));
                default -> this;
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        try {
            var withCaller = new ObjectNode().addJSONable("caller", this.caller);
            var withArguments = arguments.stream()
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addJSON)
                    .build();
            return withCaller.addObject("arguments", withArguments);
        } catch (StreamException e) {
            return new ObjectNode();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(caller, arguments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invocation that)) return false;
        return Objects.equals(caller, that.caller) && Objects.equals(arguments, that.arguments);
    }
}
