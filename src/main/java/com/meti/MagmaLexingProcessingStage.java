package com.meti;

import com.meti.node.Node;

public final class MagmaLexingProcessingStage extends AbstractProcessingStage {
    public MagmaLexingProcessingStage(Node root) {
        super(root);
    }

    @Override
    protected Node processNodeTree(Node value) throws CompileException {
        Node toUse;
        if (value.is(Node.Type.Content)) {
            var input = value.apply(Attribute.Type.Value).asString();
            toUse = new MagmaNodeLexer(new Input(input)).process().orElseThrow(() -> createCannotLex(value));
        } else {
            toUse = value;
        }
        return processDependents(toUse);
    }

    private CompileException createCannotLex(Node value) {
        return new CompileException("Cannot lex: " + value);
    }

    @Override
    protected Node processFieldNode(Node field) throws CompileException {
        Node toUse;
        if (field.is(Node.Type.Content)) {
            var input = new Input(field.apply(Attribute.Type.Value).asString());
            toUse = new DeclarationLexer(input)
                    .process()
                    .orElseThrow(() -> createCannotLex(field));
        } else {
            toUse = field;
        }
        return processDependents(toUse);
    }
}
