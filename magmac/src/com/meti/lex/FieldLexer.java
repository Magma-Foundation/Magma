package com.meti.lex;

import com.meti.Content;
import com.meti.FieldNode;

import java.util.Optional;

public record FieldLexer(String value) {
    public Optional<FieldNode> lex() {
        var separator = value().indexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = value().substring(0, separator).strip();
        var member = value().substring(separator + 1).strip();
        var parentNode = new Content(parent, 0);
        var node = new FieldNode(parentNode, member);
        return Optional.of(node);
    }
}