package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class OrRule implements Rule {
    private final Rule first;
    private final Rule second;

    public OrRule(Rule first, Rule second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Option<NodePrototype> apply(JavaString input) {
        return first.apply(input).or(second.apply(input));
    }
}
