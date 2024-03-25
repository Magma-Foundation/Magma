package com.meti.java;

import com.meti.Splitter;
import com.meti.node.*;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.stream.Collectors;

public record ClassLexer(String input, int indent) implements Lexer {
    public static final String ID = "class";
    public static final String FLAGS = "flags";
    public static final String INDENT = "indent";
    public static final String NAME = "name";
    public static final String BODY = "body";

    @Override
    public Optional<Node> lex() {
        var bodyEnd = input().lastIndexOf('}');
        var classIndex = input().indexOf("class ");

        if (classIndex == -1 || bodyEnd != input().length() - 1) return Optional.empty();
        var name = input().substring(classIndex + "class ".length(), input().indexOf('{')).strip();
        var body = input().substring(input().indexOf('{') + 1, bodyEnd).strip();

        var isPublic = input().startsWith("public ");
        var inputFlags = isPublic ? List.of("public") : Collections.<String>emptyList();

        var attributes = new java.util.HashMap<>(Map.of(
                INDENT, new IntAttribute(this.indent),
                FLAGS, new StringListAttribute(inputFlags),
                NAME, new StringAttribute(name)
        ));

        if (!body.isEmpty()) {
            var members = Arrays.stream(new Splitter(body).split())
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .<Node>map(child -> new Content("class-member", child, indent + 1))
                    .collect(Collectors.toList());

            attributes.put(BODY, new NodeListAttribute(members));
        }

        return Optional.of(new MapNode("class", attributes));
    }
}