package com.meti;

import java.util.Map;
import java.util.Optional;

public record ImportLexer(String stripped) implements Lexer {
    @Override
    public Option<MapNode> lex() {
        if (!stripped().startsWith("import ")) return new None<>();
        var segments = stripped().substring("import ".length());
        var separator = segments.lastIndexOf('.');
        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        var map = Map.of("parent", parent, "child", child);
        return new Some<>(new MapNode("import", map));
    }
}