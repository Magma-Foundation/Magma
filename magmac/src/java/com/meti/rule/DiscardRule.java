package com.meti.rule;

import com.meti.Tuple;
import com.meti.api.Options;
import com.meti.api.Result;
import com.meti.api.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Collections;
import java.util.Optional;

public class DiscardRule implements Rule {
    public static final Rule Empty = new DiscardRule();

    private DiscardRule() {
    }

    private Optional<String> toString1(MapNode node) {
        return Optional.of("");
    }

    @Override
    public RuleResult fromString(String value) {
        return new NodeRuleResult(new Tuple<>(new NodeAttributes(Collections.emptyMap()), Optional.empty()));
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
