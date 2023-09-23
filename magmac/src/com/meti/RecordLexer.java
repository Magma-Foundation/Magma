package com.meti;

import static com.meti.Options.$Option;

public record RecordLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var nameStart = stripped().firstIndexOfSlice("record ").$()
                    .nextBy("record ".length())
                    .$();

            var paramStart = stripped().firstIndexOfChar('(').$();
            var bodyStart = stripped().firstIndexOfChar('{').$();
            var bodyEnd = stripped().lastIndexOfChar('}').$()
                    .next()
                    .$();

            var name = stripped().slice(nameStart.to(paramStart).$());
            var bodySlice = stripped().slice(bodyStart.to(bodyEnd).$());

            var node = new MethodNode(name, "()", bodySlice);
            return node;
        });
    }
}