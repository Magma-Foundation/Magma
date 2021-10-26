package com.meti.app.compile.feature;

import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.output.CompoundOutput;
import com.meti.app.compile.node.output.NodeOutput;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockRenderer extends AbstractRenderer {
    public BlockRenderer(Node node) {
        super(node, Node.Type.Block);
    }

    @Override
    protected Output processDefined() throws AttributeException {
        var lines = node.apply(Attribute.Type.Children)
                .asNodeStream()
                .map(NodeOutput::new)
                .collect(Collectors.toList());
        var copy = new ArrayList<Output>(List.of(new StringOutput("{")));
        copy.addAll(lines);
        copy.add(new StringOutput("}"));
        return new CompoundOutput(copy);
    }
}
