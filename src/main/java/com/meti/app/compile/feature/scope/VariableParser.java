package com.meti.app.compile.feature.scope;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.parse.AbstractParser;
import com.meti.app.compile.parse.Scope;
import com.meti.app.compile.parse.State;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;

public class VariableParser extends AbstractParser {
    public VariableParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.applyToCurrent(value -> value.is(Node.Category.Variable));
    }

    private Input extractName(Node current) throws AttributeException {
        return current.apply(Attribute.Category.Value)
                .asInput();
    }

    @Override
    protected Option<State> modifyBeforeASTImpl() throws CompileException {
        var name = state.applyToCurrent(this::extractName);
        if (state.applyToScope(scope -> isDefined(scope, name))) return new None<>();
        throw new UndefinedException(name);
    }

    private boolean isDefined(Scope scope, Input name) {
        return scope.isDefined(name.toOutput().compute());
    }
}
