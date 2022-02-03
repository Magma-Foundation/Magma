package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.magma.FunctionType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.Primitive;

public final class MagmaModificationStage extends AbstractModificationStage implements MagmaParsingStageMixin {
    public MagmaModificationStage(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State createInitialState() {
        return new State().mapScope(scope -> {
            // TODO: Do generic type here later
            var type = new FunctionType(Primitive.Void);
            return scope.define(new Declaration("=", type));
        });
    }
}
