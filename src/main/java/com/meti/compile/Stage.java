package com.meti.compile;

import com.meti.compile.node.Node;

public interface Stage {
    Node apply(Node node) throws CompileException;
}
