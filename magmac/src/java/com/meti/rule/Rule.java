package com.meti.rule;

import com.meti.node.MapNode;

import java.util.Optional;

public interface Rule {
    RuleResult fromString(String value);

    Optional<String> toString(MapNode node);
}

