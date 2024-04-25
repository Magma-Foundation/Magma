package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public interface Rule {
    Option<NodePrototype> apply(JavaString input);
}
