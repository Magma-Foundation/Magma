package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class LazyRule implements Rule {
    private Optional<Rule> value = Optional.empty();
    private final Stack<String> stack = new Stack<>();

    public void set(Rule value) {
        this.value = Optional.ofNullable(value);
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input) {
        if(stack.contains(input)) return Optional.empty();

        stack.push(input);
        var optionalMapTuple = value.flatMap(internal -> internal.lexImpl(input));
        stack.pop();
        return optionalMapTuple;
    }
}
