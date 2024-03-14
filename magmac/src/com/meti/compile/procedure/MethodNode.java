package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.collect.option.Some.Some;

public record MethodNode(int indent, Option<?> moreOutputValue, List<String> annotations, String name, String type,
                         Node content) implements Node {

    @Override
    public Option<Node> findValueAsNode() {
        return Some(content);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new MethodNode(indent, moreOutputValue, annotations, name, type, value));
    }

    @Override
    public Option<String> render() {
        var exceptions = moreOutputValue().map(value -> " ? " + value).orElse("");
        var annotationsString = annotations().stream().map(annotation -> "\t".repeat(indent()) + "@" + annotation + "\n").collect(Collectors.joining());

        return Some("\n" + annotationsString + "\t".repeat(indent()) + "def " + name() + "() : " + type() + exceptions + " => " + content.render().orElse(""));
    }
}