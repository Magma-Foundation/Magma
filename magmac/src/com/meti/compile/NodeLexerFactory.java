package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.result.Result;
import com.meti.compile.node.Node;

public interface NodeLexerFactory {
    Result<Lexer<Node>, ? extends CompileException> create(JavaString input, JavaString type);
}
