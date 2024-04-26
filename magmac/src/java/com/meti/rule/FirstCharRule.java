package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class FirstCharRule implements Rule {
    private final Rule left;
    private final Rule right;
    private final char c;

    public FirstCharRule(Rule left, char c, Rule right) {
        this.left = left;
        this.right = right;
        this.c = c;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return input.splitAtFirstIndexOfCharExclusive(c)
                .flatMap(tuple -> left.fromString(tuple.a()).and(right.fromString(tuple.b())))
                .map(tuple -> tuple.a().merge(tuple.b()));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        return left.fromNode(node).and(right.fromNode(node)).map(tuple -> tuple.a().concatChar(c).concatOwned(tuple.b()));
    }
}
