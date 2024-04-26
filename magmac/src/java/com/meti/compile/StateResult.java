package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.option.Option;

import java.util.Optional;

public interface StateResult {
    Option<Node> findInstanceValue();

    Option<Node> findStaticValue();
}
