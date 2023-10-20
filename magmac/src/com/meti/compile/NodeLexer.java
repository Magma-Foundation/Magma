package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.option.Option;
import com.meti.compile.node.Node;

public interface NodeLexer extends Lexer<Node> {

    @Override
    Iterator<Node> lex();
}
