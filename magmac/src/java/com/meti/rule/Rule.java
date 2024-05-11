package com.meti.rule;

import com.meti.api.result.Result;
import com.meti.node.MapNode;

public interface Rule {
    RuleResult fromString(String value);

    Result<String, RuleException> toString(MapNode node);
}

