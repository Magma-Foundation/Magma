package com.meti.app.compile.stage;

import com.meti.app.compile.node.Node;

public interface Stage {
    Node transformNodeAST(Node node) throws CompileException;
}
