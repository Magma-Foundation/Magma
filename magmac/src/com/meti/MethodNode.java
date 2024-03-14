package com.meti;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.Some.Some;

public record MethodNode(int indent, Option<?> moreOutputValue, List<String> annotations, String name, String type,
                         Node content) implements Node {
    @Override
    public Option<String> render() {
        var exceptions = moreOutputValue().map(value -> " ? " + value).orElse("");
        var annotationsString = annotations().stream().map(annotation -> "\t".repeat(indent()) + "@" + annotation + "\n").collect(Collectors.joining());

        return Some("\n" + annotationsString + "\t".repeat(indent()) + "def " + name() + "() : " + type() + exceptions + " => " + content.render().orElse(""));
    }
}