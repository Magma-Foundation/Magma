package com.meti.app.compile.stage;

import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;

public interface Formatter {
    Node apply(Node node) throws AttributeException;
}
