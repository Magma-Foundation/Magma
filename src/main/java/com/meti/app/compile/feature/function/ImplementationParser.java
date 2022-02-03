package com.meti.app.compile.feature.function;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.magma.FunctionType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.TypeAttribute;
import com.meti.app.compile.parse.AbstractParser;
import com.meti.app.compile.parse.MagmaResolver;
import com.meti.app.compile.parse.State;
import com.meti.app.compile.stage.CompileException;

public class ImplementationParser extends AbstractParser {
    public ImplementationParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.queryCurrent(value -> value.is(Node.Category.Implementation));
    }

    @Override
    protected State modifyBeforeASTImpl() throws CompileException {
        var element = state.getCurrent();
        var identity = element.apply(Attribute.Category.Identity).asNode();
        var expectedType = identity.apply(Attribute.Category.Type).asType();
        var value = element.apply(Attribute.Category.Value).asNode();

        var assignableTypes = new MagmaResolver(value, state.getScope()).resolve();
        var typeToSet = isAssignableTo(expectedType, assignableTypes);

        var newIdentity = identity.with(Attribute.Category.Type, new TypeAttribute(typeToSet));
        var newElement = element.with(Attribute.Category.Identity, new NodeAttribute(newIdentity));
        return state.apply(newElement);
    }

    @Override
    protected State onExitImpl() throws CompileException {
        try {
            var current = state.getCurrent();
            var oldIdentity = current.apply(Attribute.Category.Identity).asNode();
            var returnType = oldIdentity.apply(Attribute.Category.Type).asType();
            var parameterTypes = current.apply(Attribute.Category.Parameters)
                    .asStreamOfNodes()
                    .map(parameter -> parameter.apply(Attribute.Category.Type))
                    .map(Attribute::asType)
                    .foldRight(List.<Node>createList(), List::add);
            var type = new FunctionType(returnType, parameterTypes);
            var newIdentity = oldIdentity.with(Attribute.Category.Type, new TypeAttribute(type));
            return state.mapScope(scope -> scope.define(newIdentity));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Type isAssignableTo(Type expectedType, Type actualType) throws CompileException {
        if (expectedType.is(Node.Category.Implicit)) {
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
