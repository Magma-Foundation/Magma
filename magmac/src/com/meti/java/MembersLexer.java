package com.meti.java;

import com.meti.Splitter;
import com.meti.node.Content;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.NodeListAttribute;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MembersLexer implements Lexer {
    private final String value;

    public MembersLexer(String value) {
        this.value = value;
    }

    @Override
    public Optional<Node> lex() {
        var statements = Arrays.stream(new Splitter(value).split())
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .<Node>map(value -> new Content("statement", value, 0))
                .collect(Collectors.toList());

        return Optional.of(new MapNode("members", Map.of(
                "values", new NodeListAttribute(statements)
        )));
    }
}
