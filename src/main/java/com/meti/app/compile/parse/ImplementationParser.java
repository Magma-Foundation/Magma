package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.stage.CompileException;

public class ImplementationParser extends UnitedParser {
    public ImplementationParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid(State state) {
        return state.queryCurrent(value -> value.is(Node.Type.Implementation));
    }

    @Override
    protected State onExitImpl() throws CompileException {
        var element = state.getCurrent();
        var identity = element.apply(Attribute.Type.Identity).asNode();
        var expectedType = identity.apply(Attribute.Type.Type).asNode();
        var value = element.apply(Attribute.Type.Value).asNode();

        var assignableTypes = new MagmaResolver(value, state.getScope()).resolveNodeToMultiple();
        var typeToSet = isAssignableTo(expectedType, assignableTypes);

        var newIdentity = identity.with(Attribute.Type.Type, new NodeAttribute(typeToSet));
        var newElement = element.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
        return state.apply(newElement);
    }

    private Node isAssignableTo(Node expectedType, com.meti.api.collect.java.List<Node> assignableTypes) throws CompileException {
        Node typeToSet;
        if (expectedType.is(Node.Type.Implicit)) {
            typeToSet = assignableTypes.first().orElse(Primitive.Void);
        } else if (assignableTypes.contains(expectedType)) {
            typeToSet = expectedType;
        } else {
            var format = "Expected function to return '%s', but was actually '%s'.";
            var message = format.formatted(expectedType, assignableTypes);
            throw new CompileException(message);
        }
        return typeToSet;
    }
}
