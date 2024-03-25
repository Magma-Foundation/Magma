package com.meti.lex;

import com.meti.node.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public record InvocationLexer(String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        var argStart = input().indexOf('(');
        if (argStart == -1) return Optional.empty();

        var argEnd = input().indexOf(')');
        if (argEnd == -1) return Optional.empty();

        var callerString = input().substring(0, argStart).strip();
        var value1 = new Content(callerString, 0);
        var collect = Arrays.stream(input().substring(argStart + 1, argEnd)
                        .strip()
                        .split(","))
                .map(String::strip)
                .filter(argString -> !argString.isEmpty())
                .<Node>map(value -> new Content(value, 0))
                .toList();

        var invocationNode = (Node) new MapNode(Map.of(
                "caller", new NodeAttribute(value1),
                "arguments", new NodeListAttribute(collect)
        ));
        return Optional.of(invocationNode);
    }
}