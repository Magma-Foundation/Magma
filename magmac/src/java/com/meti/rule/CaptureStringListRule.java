package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.MapNodePrototype;
import com.meti.node.NodePrototype;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.List;

public class CaptureStringListRule implements Rule{
    private final String name;
    private final String delimiter;

    public CaptureStringListRule(String name, String delimiter) {
        this.name = name;
        this.delimiter = delimiter;
    }

    @Override
    public Option<NodePrototype> apply(JavaString input) {
        var splits = input.splitBySlice(delimiter);
        return new Some<>(new MapNodePrototype().withListOfStrings(name, splits));
    }
}
