package com.meti;

import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.Node;
import com.meti.node.StringAttribute;

import java.util.Optional;
import java.util.function.Function;

public record FieldNode(Node parentOutput, String member) implements Node {
    @Override
    public Optional<String> render() {
        return Optional.of(this.parentOutput().apply("value").flatMap(Attribute::asString).orElseThrow() + "." + member());
    }

    @Override
    public Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        return mapper.apply(parentOutput).map(parent -> new FieldNode(parent, member));
    }

    @Override
    public Optional<Attribute> apply(String name) {
        if(name.equals("value")) return findValue().map(StringAttribute::new);
        if(name.equals("indent")) return findIndent().map(IntAttribute::new);
        return Optional.empty();
    }

    private Optional<String> findValue() {
        throw new UnsupportedOperationException();
    }

    private Optional<Integer> findIndent() {
        throw new UnsupportedOperationException();
    }
}