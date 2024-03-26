package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public class LazyRule implements Rule {
    private Optional<Rule> value = Optional.empty();

    public void setValue(Rule value) {
        this.value = Optional.ofNullable(value);
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        return value.flatMap(internal -> internal.apply(input));
    }
}
