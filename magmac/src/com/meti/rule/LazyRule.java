package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class LazyRule implements Rule {
    private Optional<Rule> value = Optional.empty();

    public void set(Rule value) {
        this.value = Optional.ofNullable(value);
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    private final Stack<String> stack = new Stack<>();

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        if(this.stack.contains(input)) return Optional.empty();

        this.stack.push(input);
        var optionalMapTuple = value.flatMap(internal -> internal.lex(input, stack));
        this.stack.pop();
        return optionalMapTuple;
    }
}
