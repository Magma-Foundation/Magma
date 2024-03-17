package com.meti.compile.external;

import com.meti.collect.JavaList;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import java.util.List;

public class PackageNode implements Node {
    @Override
    public Option<String> render() {
        return None.None();
    }

    @Override
    public boolean is(String name) {
        return name.equals("package");
    }

    @Override
    public Option<Node> with(String name, Attribute attribute) {
        return switch (name) {
            case "children" -> attribute.asListOfNodes()
                    .map(JavaList::unwrap)
                    .flatMap(this::withChildren);
            case "value" -> attribute.asNode().flatMap(this::withValue);
            default -> None.None();
        };
    }

    private Option<Node> withChildren(List<? extends Node> children) {
        return None.None();
    }

    private Option<Node> withValue(Node value) {
        return None.None();
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
                return None.None();
        }
    }

    private Option<List<? extends Node>> findChildren() {
        return None.None();
    }

    private Option<Node> findValueAsNode() {
        return None.None();
    }

    private Option<String> findValueAsString() {
        return None.None();
    }

    private Option<Integer> findIndent() {
        return None.None();
    }

    private Option<JavaList<JavaString>> findFlags() {
        return None.None();
    }

    private Option<JavaString> findName() {
        return None.None();
    }
}
