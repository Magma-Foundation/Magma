package com.meti;

public final class MagmaLexingProcessingStage extends AbstractProcessingStage {
    public MagmaLexingProcessingStage(Node root) {
        super(root);
    }

    @Override
    protected Node processNodeTree(Node value) throws CompileException {
        Node toUse;
        if (value.is(Node.Type.Content)) {
            var input = value.apply(Attribute.Type.Value).asString();
            toUse = new MagmaNodeLexer(input).process().orElseThrow(() -> new CompileException("Cannot lex: " + value));
        } else {
            toUse = value;
        }
        return processDependents(toUse);
    }

    @Override
    protected Node processFieldNode(Node field) {
        return field;
    }
}
