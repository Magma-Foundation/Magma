package com.meti.app.compile.feature;

import com.meti.api.StreamException;
import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.CompoundOutput;
import com.meti.app.compile.node.output.NodeOutput;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

import java.util.ArrayList;
import java.util.List;

public class BlockRenderer extends AbstractRenderer {
    public BlockRenderer(Node node) {
        super(node, Node.Type.Block);
    }

    @Override
    protected Output processDefined() throws CompileException {
        var lines = renderChildren();
        var copy = new ArrayList<Output>(List.of(new StringOutput("{")));
        copy.addAll(lines);
        copy.add(new StringOutput("}"));
        return new CompoundOutput(copy);
    }

    private ArrayList<Output> renderChildren() throws CompileException {
        try {
            return node.apply(Attribute.Type.Children)
                    .asNodeStream()
                    .map(NodeOutput::new)
                    .foldRight(new ArrayList<>(), (list, child) -> {
                        list.add(child);
                        return list;
                    });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
