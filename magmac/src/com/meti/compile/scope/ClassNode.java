package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record ClassNode(JavaList<JavaString> flags, JavaString name,
                        Node value) implements Node {
    private Option<Node> findValueAsNode() {
        return Some(value);
    }

    private Option<Node> withValue(Node value) {
        return Some(new ClassNode(flags, name, value));
    }

    @Override
    public boolean is(String name) {
        return name.equals("object");
    }

    @Override
    public Option<Node> with(String name, Attribute attribute) {
        return switch (name) {
            case "children" -> attribute.asListOfNodes()
                    .map(JavaList::unwrap)
                    .flatMap(this::withChildren);
            case "value" -> attribute.asNode().flatMap(this::withValue);
            default -> None();
        };
    }

    private Option<Node> withChildren(List<? extends Node> children) {
        return None();
    }

    @Override
    public Option<Attribute> apply(String name) {
        switch (name) {
            case "children":
                return findChildren()
                        .map(JavaList::new)
                        .map(NodeListAttribute::new);
            case "value":
                return findValueAsNode()
                        .<Attribute>map(NodeAttribute::new)
                        .orLazy(() -> findValueAsString()
                                .map(JavaString::new)
                                .map(StringAttribute::new));
            case "flags":
                return findFlags().map(StringListAttribute::new);
            case "name":
                return findName().map(StringAttribute::new);
            case "indent":
                return findIndent().map(IntegerAttribute::new);
            default:
                return None();
        }
    }

    private Option<List<? extends Node>> findChildren() {
        return None();
    }

    private Option<String> findValueAsString() {
        return None();
    }

    private Option<Integer> findIndent() {
        return None();
    }

    private Option<JavaList<JavaString>> findFlags() {
        return None();
    }

    private Option<JavaString> findName() {
        return None();
    }
}