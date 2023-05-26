package com.meti.node;

import com.meti.JavaClass;

import java.util.Optional;

public record ImplementationLexer(String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        if (input().contains(JavaClass.ClassKeyword)) {
            var prefixIndex = input().indexOf(JavaClass.ClassKeyword);
            var bodyStart = input().indexOf('{');
            var bodyEnd = input().lastIndexOf('}');
            var bodyString = input().substring(bodyStart, bodyEnd + 1);
            var body = new Content(bodyString);
            var name = input().substring(prefixIndex + JavaClass.ClassKeyword.length(), bodyStart).strip();

            return Optional.of(new Implementation(name, body, Definition.Flag.Class));
        }
        return Optional.empty();
    }
}