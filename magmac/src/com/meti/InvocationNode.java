package com.meti;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.Some.Some;

public record InvocationNode(List<String> list) {
    Option<String> render() {
        var args = list()
                .stream()
                .collect(Collectors.joining(", ", "(", ")"));

        return Some("Paths.get" + args);
    }
}