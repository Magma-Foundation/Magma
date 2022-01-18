package com.meti.app.compile.lex;

import com.meti.api.option.Option;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Node;

public interface Lexer {
    Option<Node> lex() throws CompileException;
}
