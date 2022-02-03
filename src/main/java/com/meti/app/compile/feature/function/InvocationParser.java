package com.meti.app.compile.feature.function;

import com.meti.app.compile.common.Line;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.parse.AbstractParser;
import com.meti.app.compile.parse.MagmaResolver;
import com.meti.app.compile.parse.State;
import com.meti.app.compile.stage.CompileException;

public class InvocationParser extends AbstractParser {
    public InvocationParser(State state) {
        super(state);
    }

    @Override
    protected State onParseImpl() throws CompileException {
        var current = state.getCurrent();
        var caller = current.apply(Attribute.Type.Caller).asNode();

        var type = new MagmaResolver(caller, state.getScope()).resolve();
        if (!type.is(Node.Role.Function)) {
            var format = "Caller type '%s' is not a function.";
            var message = format.formatted(type);
            throw new CompileException(message);
        }

        var isInBlock = state.getScope()
                .getParents()
                .last()
                .map(parent -> parent.is(Node.Role.Block))
                .orElse(false);

        if (isInBlock) {
            return state.mapCurrent(Line::new);
        } else {
            return state;
        }
    }

    @Override
    protected boolean isValid() {
        return state.queryCurrent(value -> value.is(Node.Role.Invocation));
    }
}
