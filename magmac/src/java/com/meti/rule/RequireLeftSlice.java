package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class RequireLeftSlice implements Rule {
    private final String slice;
    private final Rule before;

    public RequireLeftSlice(String slice, Rule before) {
        this.slice = slice;
        this.before = before;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return input.firstRangeOfSlice(slice).flatMap(before -> this.before.fromString(input.sliceTo(before.startIndex())));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        return before.fromNode(node).map(value -> value.prependSlice(slice));
    }
}
