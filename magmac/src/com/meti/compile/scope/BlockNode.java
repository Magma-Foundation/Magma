package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record BlockNode(int indent, List<? extends Node> children) implements Node {
    private Option<List<? extends Node>> findChildren() {
        return Some(children);
    }

    private Option<Node> withChildren(List<? extends Node> children) {
        return Some(new BlockNode(indent, children));
    }

    private Option<Integer> findIndent() {
        return Some(indent);
    }

    @Override
    public Option<String> render() {
        return Some(children()
                .stream()
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining("", "{", "\n" + "\t".repeat(indent) + "}")));
    }

    @Override
    public boolean is(String name) {
        return name.equals("block");
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

    private Option<Node> withValue(Node value) {
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

    private Option<Node> findValueAsNode() {
        return None();
    }

    private Option<String> findValueAsString() {
        return None();
    }

    private Option<JavaList<JavaString>> findFlags() {
        return None();
    }

    private Option<JavaString> findName() {
        return None();
    }
}