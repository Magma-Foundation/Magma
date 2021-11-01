package com.meti.app.compile.node.attribute;

import com.meti.app.compile.CompileException;

public class NodeException extends CompileException {
    public NodeException(String message) {
        super(message);
    }

    public NodeException(Throwable cause) {
        super(cause);
    }
}
