package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Attribute;
import com.meti.node.MapNodePrototype;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.stream.Collectors;

public class StringListRule implements Rule{
    private final String name;
    private final String delimiter;

    public StringListRule(String name, String delimiter) {
        this.name = name;
        this.delimiter = delimiter;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        var splits = input.splitBySlice(delimiter);
        return new Some<>(new MapNodePrototype().withListOfStrings(name, splits));
    }

    @Override
    public Option<JavaString> fromNode(Node node){
        return node.apply(name).flatMap(Attribute::asListOfStrings).map(list -> {
            return list.stream()
                    .map(JavaString::value)
                    .collect(Collectors.joining(delimiter));
        }).map(JavaString::new);
    }
}
