package com.meti;

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
