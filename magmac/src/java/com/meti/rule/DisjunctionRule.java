package com.meti.rule;

import com.meti.api.Result;
import com.meti.api.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DisjunctionRule(List<Rule> rules) implements Rule {
    public static Rule Or(Rule... rules) {
        return new DisjunctionRule(List.of(rules));
    }

    private Optional<String> toString1(MapNode node) {
        for (Rule rule : rules) {
            var optional = Options.toNative(rule.toString(node).value());
            if (optional.isPresent()) return optional;
        }

        return Optional.empty();
    }

    @Override
    public RuleResult fromString(String value) {
        var errors = new ArrayList<RuleResult>();

        for (Rule rule : rules) {
            var result = rule.fromString(value);
            if (result.isValid()) {
                return result;
            } else {
                errors.add(result);
            }
        }

        return new ParentRuleResult("Disjunction invalid.", value, errors);
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
