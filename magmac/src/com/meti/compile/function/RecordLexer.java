package com.meti.compile.function;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.Node;

import static com.meti.api.option.Options.$Option;

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

            var name = stripped().sliceBetween(nameStart.to(paramStart).$());
            var bodySlice = stripped().sliceBetween(bodyStart.to(bodyEnd).$());

            var node = new MethodNode(name, "()", bodySlice);
            return node;
        });
    }
}