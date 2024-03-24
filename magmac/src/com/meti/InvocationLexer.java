package com.meti;

import java.util.Arrays;
import java.util.Optional;

public record InvocationLexer(String input) {
    Optional<InvocationNode> lexInvocation() {
        var argStart = input().indexOf('(');
        if (argStart == -1) return Optional.empty();

        var argEnd = input().indexOf(')');
        if (argEnd == -1) return Optional.empty();

        var callerString = input().substring(0, argStart).strip();
        var value1 = new Content(0, callerString);
        var collect = Arrays.stream(input().substring(argStart + 1, argEnd)
                        .strip()
                        .split(","))
                .map(String::strip)
                .filter(argString -> !argString.isEmpty())
                .<Node>map(value -> new Content(0, value))
                .toList();

        var invocationNode = new InvocationNode(value1, collect);
        return Optional.of(invocationNode);
    }
}