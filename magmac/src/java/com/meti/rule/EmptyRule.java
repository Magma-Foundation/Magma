package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.MapNodePrototype;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class EmptyRule implements Rule {
    @Override
    public Option<JavaString> fromNode(Node node) {
        return new Some<>(JavaString.EMPTY);
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return input.isEmpty() ? new Some<>(new MapNodePrototype()) : new None<>();
    }
}
