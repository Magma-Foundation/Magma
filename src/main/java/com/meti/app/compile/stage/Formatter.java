package com.meti.app.compile.stage;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.AttributeException;

public interface Formatter {
    Node apply(Node node) throws AttributeException;
}
