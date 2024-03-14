package com.meti;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.Some.Some;

public record BlockNode(int indent, List<String> stringStream) implements Node {
    @Override
    public Option<String> render() {
        return Some(stringStream().stream().collect(Collectors.joining("", "{\n", "\t".repeat(indent()) + "}\n")));
    }
}