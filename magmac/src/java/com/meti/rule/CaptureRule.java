package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.MapNodePrototype;
import com.meti.node.NodePrototype;
import com.meti.option.Option;
import com.meti.option.Some;

public class CaptureRule implements Rule{
    private final String name;

    public CaptureRule(String name) {
        this.name = name;
    }

    @Override
    public Option<NodePrototype> apply(JavaString input) {
        return new Some<>(new MapNodePrototype().withString(name, input));
    }
}
