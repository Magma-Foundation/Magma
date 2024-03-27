package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RangeRule implements Rule {
    private final char start;
    private final char endInclusive;

    public RangeRule(char start, char endInclusive) {
        this.start = start;
        this.endInclusive = endInclusive;
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        if (input.length() == 1) {
            var first = input.charAt(0);
            if (start <= first && first <= endInclusive) {
                return Optional.of(Collections.emptyMap());
            }
        }

        return Optional.empty();
    }

    private Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input) {
var result = lex(input);
        return result;
    }
}
