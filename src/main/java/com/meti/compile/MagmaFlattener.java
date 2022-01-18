package com.meti.compile;

import com.meti.compile.node.Node;

public class MagmaFlattener extends AbstractStage {
    @Override
    public Node apply(Node node) throws CompileException {
        return node;
    }

    @Override
    protected Node transformDefinition(Node definition) {
        return definition;
    }
}
