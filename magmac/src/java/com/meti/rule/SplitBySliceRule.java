package com.meti.rule;

import com.meti.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class SplitBySliceRule implements Rule {
    protected final Rule leftRule;
    protected final String slice;
    protected final Rule rightRule;

    public SplitBySliceRule(Rule leftRule, String slice, Rule rightRule) {
        this.leftRule = leftRule;
        this.slice = slice;
        this.rightRule = rightRule;
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        var separator = applyOperation(value);
        if (separator == -1) return Optional.empty();

        var parent = value.substring(0, separator);
        var firstMap = leftRule.apply(parent).map(Tuple::a);
        if (firstMap.isEmpty()) return Optional.empty();

        var child = value.substring(separator + slice.length());
        var secondMap = rightRule.apply(child).map(Tuple::a);
        if (secondMap.isEmpty()) return Optional.empty();

        var attributes = new HashMap<String, String>();
        attributes.putAll(firstMap.orElseThrow());
        attributes.putAll(secondMap.orElseThrow());
        return Optional.<Map<String, String>>of(attributes).map(map -> new Tuple<>(map, Optional.empty()));
    }

    protected abstract int applyOperation(String value);
}
