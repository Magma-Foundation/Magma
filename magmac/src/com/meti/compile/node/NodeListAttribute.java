package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;

import java.util.List;

public class NodeListAttribute implements Attribute {
    public static String Type = "nodeList";
    private final JavaList<? extends Node> values;

    public NodeListAttribute(JavaList<? extends Node> values) {
        this.values = values;
    }

    @Override
    public Option<JavaList<? extends Node>> asListOfNodes() {
        return Some.Some(values);
    }
}
