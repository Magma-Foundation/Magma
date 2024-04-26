package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
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
    public Option<NodePrototype> fromString(JavaString input) {
        return first.fromString(input).or(second.fromString(input));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        throw new UnsupportedOperationException();
    }
}
