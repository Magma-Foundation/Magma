package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

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

    protected abstract int computeOffset();

    protected abstract int applyOperation(String value);

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        var separator = applyOperation(value);
        if (separator == -1) return Optional.empty();

        var parent = value.substring(0, separator);
        var firstMap = leftRule.fromString(parent).map(Tuple::left);
        if (firstMap.isEmpty()) return Optional.empty();

        var child = value.substring(separator + computeOffset());
        var secondMap = rightRule.fromString(child).map(Tuple::left);

        if (secondMap.isEmpty()) return Optional.empty();

        var attributes = firstMap.orElseThrow().add(secondMap.orElseThrow());
        return Optional.of(new Tuple<>(attributes, Optional.empty()));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        throw new UnsupportedOperationException();
    }
}
