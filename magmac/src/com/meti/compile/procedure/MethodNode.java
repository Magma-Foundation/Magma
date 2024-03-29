package com.meti.compile.procedure;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class MethodNode implements Node {
    protected final int indent;
    protected final Option<?> moreOutputValue;
    protected final List<String> annotations;
    protected final JavaString name;
    protected final String type;

    public MethodNode(int indent, Option<?> moreOutputValue, List<String> annotations, JavaString name, String type) {
        this.indent = indent;
        this.moreOutputValue = moreOutputValue;
        this.annotations = annotations;
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean is(String name) {
        return name.equals("method");
    }

    @Override
    public Option<String> render() {
        var exceptions = moreOutputValue.map(value -> " ? " + value).orElse("");
        var annotationsString = annotations.stream().map(annotation -> "\t".repeat(indent) + "@" + annotation + "\n").collect(Collectors.joining());

        return Some("\n" + annotationsString + "\t".repeat(indent) + "def " + name + "() : " + type + exceptions + renderContent());
    }

    protected String renderContent() {
        return "";
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
