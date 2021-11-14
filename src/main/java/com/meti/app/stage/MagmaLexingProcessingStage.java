package com.meti.app.stage;

import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.attribute.Attribute;
import com.meti.app.node.Node;
import com.meti.app.process.clang.DeclarationLexer;
import com.meti.app.process.magma.MagmaNodeLexer;

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
