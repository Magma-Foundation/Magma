package com.meti.app.stage;

import com.meti.app.CompileException;
import com.meti.app.node.Content;
import com.meti.app.node.Node;
import com.meti.app.process.clang.CFieldRenderer;
import com.meti.app.process.clang.CNodeRenderer;

public final class CRenderingProcessingStage extends AbstractProcessingStage {
    public CRenderingProcessingStage(Node root) {
        super(root);
    }

    @Override
    protected Node processNodeTree(Node value) throws CompileException {
        var withNodes = processDependents(value);
        return new Content(new CNodeRenderer(withNodes).process()
                .orElseThrow(() -> new CompileException("Cannot render: " + withNodes)));
    }

    @Override
    protected Node processFieldNode(Node field) throws CompileException {
        var dependents = processDependents(field);
        return new Content(new CFieldRenderer(dependents)
                .process()
                .orElseThrow(() -> new CompileException("Cannot render field: " + field)));
    }
}
