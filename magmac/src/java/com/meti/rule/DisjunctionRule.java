package com.meti.rule;

import com.meti.node.MapNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DisjunctionRule(List<Rule> rules) implements Rule {
    public static Rule Or(Rule... rules) {
        return new DisjunctionRule(List.of(rules));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        for (Rule rule : rules) {
            var optional = rule.toString(node);
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
}
