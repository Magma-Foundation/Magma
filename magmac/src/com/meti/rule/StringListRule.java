package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.StringListAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class StringListRule implements Rule {
    private final Rule required;
    private final String name;

    public StringListRule(String name, Rule required) {
        this.required = required;
        this.name = name;
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input) {
        Optional<Map<String, Attribute>> result1;
        if (required.lexImpl(input).map(tuple -> tuple.b()).isPresent()) {
            result1 = Optional.of(Map.of(name, new StringListAttribute(Collections.singletonList(input))));
        } else {
            result1 = Optional.empty();
        }
        var result = result1.<Tuple<Optional<String>, Map<String, Attribute>>>map(attributes -> new Tuple<>(Optional.empty(), attributes));
        return result;
    }
}
