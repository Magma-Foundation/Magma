package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class IgnoreRight implements Rule {
    private final char c;
    private final Rule before;

    public IgnoreRight(Rule before, char c) {
        this.c = c;
        this.before = before;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return input.firstIndexOfChar(c).flatMap(before -> this.before.fromString(input.sliceTo(before)));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        throw new UnsupportedOperationException();
    }
}
