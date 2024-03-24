package com.meti;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public record ClassLexer(String input, int indent1) {
    Optional<ClassNode> lex() {
        var bodyEnd = input().lastIndexOf('}');
        var classIndex = input().indexOf("class ");

        if (classIndex == -1 || bodyEnd != input().length() - 1) return Optional.empty();
        var name = input().substring(classIndex + "class ".length(), input().indexOf('{')).strip();
        var body = input().substring(input().indexOf('{') + 1, bodyEnd).strip();

        var body1 = new Content(indent1(), body);
        var isPublic = input().startsWith("public ");
        var inputFlags = isPublic ? Set.of("public") : Collections.<String>emptySet();

        return Optional.of(new ClassNode(indent1(), inputFlags, name, body1));
    }
}