package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class ListRule implements Rule {
    protected final Rule value;
    private final LazyRule lazy = new LazyRule();
    private final Rule root;

    public ListRule(Rule value) {
        this.value = value;
        root = OrRule.Or(EmptyRule.EMPTY, value, new AndRule(this.value, lazy));
        lazy.set(root);
    }

    protected Rule createAnd(LazyRule lazy) {
        return new AndRule(value, lazy);
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        return root.lex(input, stack);
    }
}
