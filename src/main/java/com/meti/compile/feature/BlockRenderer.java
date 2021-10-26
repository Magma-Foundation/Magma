package com.meti.compile.feature;

import com.meti.clang.AbstractRenderer;
import com.meti.compile.Node;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.output.CompoundOutput;
import com.meti.compile.output.NodeOutput;
import com.meti.compile.output.Output;
import com.meti.compile.output.StringOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockRenderer extends AbstractRenderer {
    public BlockRenderer(Node node) {
        super(node, Node.Type.Block);
    }

    @Override
    protected Output processValid() {
        var lines = node.apply(Attribute.Type.Children)
                .map(Attribute::asNodeStream)
                .orElse(Stream.empty())
                .map(NodeOutput::new)
                .collect(Collectors.toList());
        var copy = new ArrayList<Output>(List.of(new StringOutput("{")));
        copy.addAll(lines);
        copy.add(new StringOutput("}"));
        return new CompoundOutput(copy);
    }
}
