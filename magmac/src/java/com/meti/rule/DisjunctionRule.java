package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.List;
import java.util.Optional;

public record DisjunctionRule(List<Rule> rules) implements Rule {
    public static Rule Or(Rule... rules) {
        return new DisjunctionRule(List.of(rules));
    }

    private Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        return rules.stream()
                .map(rule -> rule.fromString(value).unwrap())
                .flatMap(Optional::stream)
                .findFirst();
    }

    @Override
    public Optional<String> toString(MapNode node) {
        for (Rule rule : rules) {
            var optional = rule.toString(node);
            if(optional.isPresent()) return optional;
        }

        return Optional.empty();
    }

    @Override
    public RuleResult fromString(String value) {
        return fromString1(value).<RuleResult>map(NodeRuleResult::new).orElseGet(() -> new ErrorRuleResult("", ""));
    }
}
