package com.meti.feature.assign;

import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;

public record AssignmentLexer(NativeString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        return input().firstIndexOfChar('=').map(equals -> {
            var split = input().splitExcludingAt(equals);
            var name = split.left();
            var value = split.right();
            return new Assignment(name, value);
        });
    }
}