package com.meti.rule;

import com.meti.api.Result;
import com.meti.api.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.Options;

import java.util.Optional;

public class LazyRule implements Rule {
    private Optional<Rule> parent = Optional.empty();

    public void setRule(Rule rule) {
        parent = Optional.of(rule);
    }

    private Optional<String> toString1(MapNode node) {
        return parent.flatMap(inner -> Options.toNative(inner.toString(node).value()));
    }

    @Override
    public RuleResult fromString(String value) {
        return parent.map(inner -> inner.fromString(value)).orElseGet(() -> new ErrorRuleResult("No parent rule was set for lazy rule.", value));
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
