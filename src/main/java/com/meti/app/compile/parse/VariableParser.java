package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;

public class VariableParser extends UnitedParser {
    protected VariableParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid(State state) {
        return state.queryCurrent(value -> value.is(Node.Type.Variable));
    }

    @Override
    protected State onEnterImpl() throws CompileException {
        var value = state.getCurrent().apply(Attribute.Type.Value).asInput();
        var format = value.toOutput().compute();
        if (!state.getScope().isDefined(format)) {
            throw new CompileException("'%s' is not defined.".formatted(format));
        } else {
            return state.apply(state.getCurrent());
        }
    }
}
