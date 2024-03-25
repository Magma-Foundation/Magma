package com.meti.java;

import com.meti.node.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public record InvokeLexer(String input) implements Lexer {

    public static final String ID = "invoke";
    public static final String CALLER = "caller";
    public static final String ARGUMENTS = "arguments";

    @Override
    public Optional<Node> lex() {
        var argStart = input().indexOf('(');
        if (argStart == -1) return Optional.empty();

        var argEnd = input().indexOf(')');
        if (argEnd == -1) return Optional.empty();

        var callerString = input().substring(0, argStart).strip();
        var value1 = new Content("value", callerString, 0);
        var collect = Arrays.stream(input().substring(argStart + 1, argEnd)
                        .strip()
                        .split(","))
                .map(String::strip)
                .filter(argString -> !argString.isEmpty())
                .<Node>map(value -> new Content("value", value, 0))
                .toList();

        var invocationNode = (Node) new MapNode(ID, Map.of(
                CALLER, new NodeAttribute(value1),
                ARGUMENTS, new NodeListAttribute(collect)
        ));
        return Optional.of(invocationNode);
    }
}