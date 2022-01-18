package com.meti.app.compile;

import com.meti.app.compile.node.Node;

public interface Stage {
    Node apply(Node node) throws CompileException;
}
