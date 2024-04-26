package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class RequireRightSlice implements Rule {
    private final String c;
    private final Rule before;

    public RequireRightSlice(String c, Rule before) {
        this.c = c;
        this.before = before;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return input.firstRangeOfSlice(c).flatMap(before -> this.before.fromString(input.sliceTo(before.startIndex())));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        return before.fromNode(node).map(value -> value.appendSlice(c));
    }
}
