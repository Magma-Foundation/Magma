package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.stage.CompileException;

public class VariableVisitor extends AbstractParser {
    protected VariableVisitor(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.applyToCurrent(value -> value.is(Node.Category.Variable));
    }

    @Override
    protected State modifyBeforeASTImpl() throws CompileException {
        var name = state.applyToCurrent(this::extractName);
        if (!state.applyToScope(scope -> scope.isDefined(name))) {
            var message = "'%s' is not defined.";
            var scope = message.formatted(name);
            throw new CompileException(scope);
        }

        return state;
    }

    private String extractName(Node current) throws AttributeException {
        return current.apply(Attribute.Category.Value)
                .asInput()
                .toOutput()
                .compute();
    }
}
