package com.meti;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record MethodLexer(String stripped, int indent) implements Lexer {
    @Override
    public Option<Node> lex() {
        var paramStart = stripped().indexOf('(');
        var paramEnd = stripped().indexOf(')');
        var contentStart = stripped().indexOf('{');

        if (paramStart == -1 || paramEnd == -1 || contentStart == -1) return None();

        var keyString = stripped().substring(0, paramStart);
        var space = keyString.lastIndexOf(' ');
        if (space == -1) return None();

        var name = keyString.substring(space + 1).strip();
        var featuresString = keyString.substring(0, space).strip();

        var typeSeparator = featuresString.lastIndexOf(' ');
        var type = new TypeCompiler(featuresString.substring(typeSeparator + 1).strip()).compile();
        var annotations = Streams.from(featuresString.substring(0, typeSeparator).strip().split(" "))
                .filter(token -> token.startsWith("@"))
                .map(token -> token.substring(1))
                .map(TypeCompiler::new)
                .map(TypeCompiler::compile)
                .collect(Collectors.toList());

        var content = new Content(stripped().substring(contentStart).strip(), indent());
        var more = stripped().substring(paramEnd + 1, contentStart).strip();

        var moreOutputValue = more.startsWith("throws ") ? Some(new TypeCompiler(more.substring("throws ".length())).compile()) : None();

        return Some(new MethodNode(indent(), moreOutputValue, annotations, name, type, content));
    }
}