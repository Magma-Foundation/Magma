package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.List;
import java.util.Optional;

public record DisjunctionRule(List<Rule> rules) implements Rule {
    public static Rule Or(Rule... rules) {
        return new DisjunctionRule(List.of(rules));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return rules.stream()
                .map(rule -> rule.apply(value))
                .flatMap(Optional::stream)
                .findFirst();
    }
}
