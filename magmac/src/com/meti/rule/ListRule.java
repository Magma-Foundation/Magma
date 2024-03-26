package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public class ListRule implements Rule {
    protected final Rule value;

    public ListRule(Rule value) {
        this.value = value;
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        var lazy = new LazyRule();
        var root = OrRule.Or(Rules.EMPTY, value, createAnd(lazy));
        lazy.set(root);
        return root.lex(input).map(tuple -> tuple.b());
    }

    protected Rule createAnd(LazyRule lazy) {
        return new AndRule(value, lazy);
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }
}
