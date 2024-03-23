package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.java.JavaString;

import java.util.List;
import java.util.Objects;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class Content implements Node {
    public static String Id = "content";
    private final String value;
    private final int indent;

    public Content(JavaString value, int indent) {
        this.value = value.inner();
        this.indent = indent;
    }

    public Content(String value, int indent) {
        this.value = value;
        this.indent = indent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return indent == content.indent && Objects.equals(value, content.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, indent);
    }

    private Option<String> findValueAsString() {
        return Some(value);
    }

    private Option<Integer> findIndent() {
        return Some(indent);
    }

    @Override
    public String toString() {
        return "new Content(\"" + value + "\", " + indent + ")";
    }

    @Override
    public Option<String> render() {
        return Some(value);
    }

    @Override
    public boolean is(String name) {
        return name.equals(Content.Id);
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
                                .map(JavaString::from)
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

    private Option<Node> findValueAsNode() {
        return None();
    }

    private Option<JavaList<JavaString>> findFlags() {
        return None();
    }

    private Option<JavaString> findName() {
        return None();
    }
}
