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

    protected Rule createAnd(LazyRule lazy) {
        return new AndRule(value, lazy);
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input) {
        var lazy = new LazyRule();
        var root = OrRule.Or(EmptyRule.EMPTY, value, createAnd(lazy));
        lazy.set(root);
        var result = root.lexImpl(input).map(tuple -> tuple.b()).<Tuple<Optional<String>, Map<String, Attribute>>>map(attributes -> new Tuple<>(Optional.empty(), attributes));
        return result;
    }
}
