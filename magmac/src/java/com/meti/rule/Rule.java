package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public interface Rule {
    Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value);

    Optional<String> toString(MapNode node);
}

