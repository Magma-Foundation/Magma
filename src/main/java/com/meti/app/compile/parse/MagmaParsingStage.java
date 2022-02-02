package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.magma.FunctionType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;

public final class MagmaParsingStage extends AbstractParsingStage implements MagmaParser {
    public MagmaParsingStage(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State createInitialState() {
        return new State().mapScope(scope -> {
            // TODO: Do generic type here later
            var type = new FunctionType(Primitive.Void);
            return scope.define(new EmptyField("=", type));
        });
    }
}
