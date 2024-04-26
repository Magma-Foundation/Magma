package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Attribute;
import com.meti.node.MapNodePrototype;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;
import com.meti.option.Some;

public class CaptureStringRule implements Rule{
    private final String name;

    public CaptureStringRule(String name) {
        this.name = name;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return new Some<>(new MapNodePrototype().withString(name, input));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        return node.apply(name).flatMap(Attribute::asString);
    }
}
