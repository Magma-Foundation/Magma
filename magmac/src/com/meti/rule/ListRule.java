package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public class ListRule implements Rule {
    protected final Rule value;

    public ListRule(Rule value) {
        this.value = value;
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        var lazy = new LazyRule();
        var root = OrRule.Or(Rules.EMPTY, value, createAnd(lazy));
        lazy.setValue(root);
        return root.apply(input);
    }

    protected Rule createAnd(LazyRule lazy) {
        return new AndRule(value, lazy);
    }
}
