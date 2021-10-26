package com.meti.app.compile.feature;

import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.CompoundOutput;
import com.meti.app.compile.node.output.NodeOutput;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockRenderer extends AbstractRenderer {
    public BlockRenderer(Node node) {
        super(node, Node.Type.Block);
    }

    @Override
    protected Output processDefined() {
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
