package com.meti.app.compile.feature.function;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.TypeAttribute;
import com.meti.app.compile.parse.AbstractParser;
import com.meti.app.compile.parse.MagmaResolver;
import com.meti.app.compile.parse.State;
import com.meti.app.compile.stage.CompileException;

public class ImplementationVisitor extends AbstractParser {
    public ImplementationVisitor(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.queryCurrent(value -> value.is(Node.Role.Implementation));
    }

    @Override
    protected State onParseImpl() throws CompileException {
        var element = state.getCurrent();
        var identity = element.apply(Attribute.Type.Identity).asNode();
        var expectedType = identity.apply(Attribute.Type.Type).asType();
        var value = element.apply(Attribute.Type.Value).asNode();

        var assignableTypes = new MagmaResolver(value, state.getScope()).resolve();
        var typeToSet = isAssignableTo(expectedType, assignableTypes);

        var newIdentity = identity.with(Attribute.Type.Type, new TypeAttribute(typeToSet));
        var newElement = element.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
        return state.apply(newElement);
    }

    private Type isAssignableTo(Type expectedType, Type actualType) throws CompileException {
        if (expectedType.is(Node.Role.Implicit)) {
            return actualType.reduce();
        } else if (actualType.isAssignableTo(expectedType)) {
            return expectedType;
        } else {
            var format = "Expected function to return '%s', but was actually '%s'.";
            var message = format.formatted(expectedType, actualType);
            throw new CompileException(message);
        }
    }
}
