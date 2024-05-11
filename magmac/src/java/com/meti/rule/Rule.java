package com.meti.rule;

import com.meti.api.Result;
import com.meti.node.MapNode;

public interface Rule {
    RuleResult fromString(String value);

    Result<String, RuleException> toString(MapNode node);
}

