package com.meti.feature.assign;

import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record AssignmentLexer(NativeString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        return input.firstIndexOfChar('=').flatMap(equals -> {
            var split = input().splitExcludingAt(equals);
            var name = split.left();
            if (name.iter().anyMatch(value -> !Character.isLetter(value))) {
                return None.apply();
            } else {
                var value = split.right();
                return Some.apply(new Assignment(name, value));
            }
        });
    }
}