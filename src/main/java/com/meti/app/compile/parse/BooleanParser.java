package com.meti.app.compile.parse;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;

public class BooleanParser extends AbstractParser {
    public BooleanParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.applyToCurrent(value -> value.is(Node.Category.Boolean));
    }

    private State modifyBeforeASTImpl2() throws CompileException {
        return state.mapCurrent(value -> {
            var state = value.apply(Attribute.Category.Value).asBoolean();
            return state ? new IntegerNode(1) : new IntegerNode(0);
        });
    }

    @Override
    protected Option<State> modifyAfterASTImpl() {
        return new Some<>(modifyAfterASTImpl2());
    }

    private State modifyAfterASTImpl2() {
        return state;
    }

    @Override
    protected Option<State> modifyBeforeASTImpl() throws CompileException {
        return new Some<>(modifyBeforeASTImpl2());
    }
}
