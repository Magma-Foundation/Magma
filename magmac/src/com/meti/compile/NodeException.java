package com.meti.compile;

import com.meti.compile.node.Node;

public class NodeException extends CompileException {
    public NodeException(String message, Node value) {
        super(message + "\n\n\t" + value.toXML().value() + "\n");
    }
}
