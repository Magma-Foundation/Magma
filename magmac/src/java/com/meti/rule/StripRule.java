package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class StripRule implements Rule {
    private final Rule next;

    public StripRule(Rule next) {
        this.next = next;
    }

    @Override
    public Option<NodePrototype> apply(JavaString input) {
        return next.apply(input.strip());
    }
}
