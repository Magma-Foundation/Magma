package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public interface Rule {
    Option<JavaString> fromNode(Node node);

    Option<NodePrototype> fromString(JavaString input);
}
