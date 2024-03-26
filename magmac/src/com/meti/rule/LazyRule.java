package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public class LazyRule implements Rule {
    private Optional<Rule> value = Optional.empty();

    public void set(Rule value) {
        this.value = Optional.ofNullable(value);
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        return value.flatMap(internal -> internal.apply(input).map(tuple -> tuple.b()));
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }
}
