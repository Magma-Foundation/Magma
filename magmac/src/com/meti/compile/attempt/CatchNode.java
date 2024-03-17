package com.meti.compile.attempt;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.collect.option.None.None;

public class CatchNode implements Node {
    private final List<JavaString> exceptionTypes;
    private final JavaString exceptionName;
    private final Node value;
    private final int indent;

    public CatchNode(List<JavaString> exceptionTypes, JavaString exceptionName, Node value, int indent) {
        this.exceptionTypes = exceptionTypes;
        this.exceptionName = exceptionName;
        this.value = value;
        this.indent = indent;
    }

    private Option<Node> findValueAsNode() {
        return Some.Some(value);
    }

    private Option<Node> withValue(Node value) {
        return Some.Some(new CatchNode(exceptionTypes, exceptionName, value, indent));
    }

    @Override
    public Option<String> render() {
        var exceptionTypeString = exceptionTypes.stream()
                .map(JavaString::inner)
                .collect(Collectors.joining(" | ", "(", " " + exceptionName + ")"));

        return Some.Some( "catch " + exceptionTypeString + value.render().orElse(""));
    }

    @Override
    public boolean is(String name) {
        return name.equals("catch");
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
