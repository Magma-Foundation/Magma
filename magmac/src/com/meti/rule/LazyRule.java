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

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return value.flatMap(internal -> internal.lex(input));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }
}
