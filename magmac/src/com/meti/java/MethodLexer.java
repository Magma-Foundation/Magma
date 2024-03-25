package com.meti.java;

import com.meti.Splitter;
import com.meti.TypeCompiler;
import com.meti.node.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record MethodLexer(int indent, String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        var paramStart = input().indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var keys = input().substring(0, paramStart).strip();
        var space = keys.lastIndexOf(' ');
        if (space == -1) return Optional.empty();

        var name = keys.substring(space + 1).strip();
        var typeString = keys.substring(0, space).strip();

        var type = new TypeCompiler(typeString).compile();

        var bodyStart = input().indexOf('{');
        if (bodyStart == -1) return Optional.empty();

        var bodyEnd = input().lastIndexOf('}');
        if (bodyEnd == -1) return Optional.empty();

        var body = input().substring(bodyStart + 1, bodyEnd).strip();

        var map = new HashMap<>(Map.of(
                "indent", new IntAttribute(indent()),
                "name", new StringAttribute(name),
                "type", new StringAttribute(type)
        ));

        if (!body.isEmpty()) {
            var lines = Arrays.stream(new Splitter(body.substring(0, body.length() - 1)).split())
                    .<Node>map(input -> new Content("method-statement", input, indent + 1))
                    .collect(Collectors.toList());

            map.put("body", new NodeListAttribute(lines));
        }

        var method = new MapNode("method", map);
        return Optional.of(method);
    }
}