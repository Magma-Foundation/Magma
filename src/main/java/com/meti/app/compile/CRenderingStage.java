package com.meti.app.compile;

import com.meti.app.clang.CRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.output.Output;

public record CRenderingStage(Node root) {
    Output render() throws CompileException {
        return renderTree(root);
    }

    private Output renderTree(Node parent) throws CompileException {
        return new CRenderer(parent)
                .process()
                .mapNodes(this::renderTree);
    }
}