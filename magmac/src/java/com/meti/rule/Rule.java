package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public interface Rule {
    Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value);
}

