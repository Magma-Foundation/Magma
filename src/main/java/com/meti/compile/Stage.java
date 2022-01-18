package com.meti.compile;

import com.meti.compile.node.Node;

public interface Stage {
    Node transformNode(Node node) throws CompileException;
}
