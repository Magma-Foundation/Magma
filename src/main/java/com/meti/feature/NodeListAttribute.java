package com.meti.feature;

import com.meti.safe.SafeList;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class NodeListAttribute implements Attribute{
    private final SafeList<? extends Node> lines;

    public NodeListAttribute(SafeList<? extends Node> lines) {
        this.lines = lines;
    }

    @Override
    public Option<SafeList<? extends Node>> asListOfNodes() {
        return Some.apply(lines);
    }
}
