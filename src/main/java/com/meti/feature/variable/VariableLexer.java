package com.meti.feature.variable;

import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record VariableLexer(NativeString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        return Some.apply(new Variable(input()));
    }
}