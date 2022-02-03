package com.meti.app.compile.parse;

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

    @Override
    protected State modifyBeforeASTImpl() throws CompileException {
        return state.mapCurrent(value -> {
            var state = value.apply(Attribute.Category.Value).asBoolean();
            return state ? new IntegerNode(1) : new IntegerNode(0);
        });
    }
}
