package com.meti.compile;

import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;

public interface Formatter {
    Node apply(Node node) throws AttributeException, FormattingException;
}
