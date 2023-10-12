package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.compile.node.Node;

public interface NodeLexer extends Lexer<Node> {
    default private Option<Node> lex1() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Iterator<Node> lex() {
        return Iterators.fromOption(lex1());
    }
}
