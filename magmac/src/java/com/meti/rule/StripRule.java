package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class StripRule implements Rule {
    private final Rule next;

    public StripRule(Rule next) {
        this.next = next;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return next.fromString(input.strip());
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        return next.fromNode(node);
    }
}
