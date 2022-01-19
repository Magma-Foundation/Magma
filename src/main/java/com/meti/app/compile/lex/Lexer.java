package com.meti.app.compile.lex;

import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public interface Lexer {
    Option<Node> lex() throws CompileException;
}
