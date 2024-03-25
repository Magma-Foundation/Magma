package com.meti.java;

import com.meti.TypeCompiler;
import com.meti.node.*;

import java.util.*;
import java.util.stream.Collectors;

public record DefinitionLexer(String body, int indent) implements Lexer {
    public static final String ID = "definition";

    @Override
    public Optional<Node> lex() {
        var valueSeparator = body().indexOf('=');
        if (valueSeparator == -1) return Optional.empty();

        var before = body().substring(0, valueSeparator).strip();
        var after = body().substring(valueSeparator + 1).strip();

        var nameSeparator = before.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var keys = before.substring(0, nameSeparator).strip();
        var typeSeparator = keys.lastIndexOf(' ');
        var type = typeSeparator == -1 ? keys : keys.substring(typeSeparator + 1).strip();

        Set<String> inputFlags;
        if (typeSeparator == -1) {
            inputFlags = Collections.emptySet();
        } else {
            inputFlags = Arrays.stream(keys.substring(0, typeSeparator).strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .collect(Collectors.toSet());
        }

        var outputFlags = new ArrayList<String>();
        if (inputFlags.contains("public")) {
            outputFlags.add("pub");
        }

        if (inputFlags.contains("final")) {
            outputFlags.add("const");
        } else {
            outputFlags.add("let");
        }

        var outputType = new TypeCompiler(type).compile();

        var name = before.substring(nameSeparator + 1).strip();
        var value = new Content("value", after, 0);

        return Optional.of(new MapNode("definition", Map.of(
                "indent", new IntAttribute(indent),
                "flags", new StringListAttribute(outputFlags),
                "name", new StringAttribute(name),
                "type", new StringAttribute(outputType),
                "value", new NodeAttribute(value)
        )));
    }
}