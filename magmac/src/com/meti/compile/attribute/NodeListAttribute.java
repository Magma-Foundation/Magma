package com.meti.compile.attribute;

import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Attribute;
import com.meti.compile.Node;

public class NodeListAttribute implements Attribute {
    private final List<Node> values;

    public NodeListAttribute(List<Node> values) {
        this.values = values;
    }

    @Override
    public Option<List<Node>> asListOfNodes() {
        return Some.apply(values);
    }
}
