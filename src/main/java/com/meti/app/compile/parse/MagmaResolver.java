package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.ReferenceType;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.Union;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.primitive.Primitive;
import com.meti.app.compile.stage.CompileException;

public record MagmaResolver(Node root, Scope scope) {
    public MagmaResolver(State state) {
        this(state.getCurrent(), state.getScope());
    }

    public Type resolve() throws CompileException {
        if (root.is(Node.Category.Variable)) {
            var variableName = root.apply(Attribute.Category.Value).asInput()
                    .toOutput()
                    .compute();
            return scope.lookup(variableName)
                    .map(node -> node.apply(Attribute.Category.Type).asType())
                    .orElseThrow(() -> {
                        var format = "'%s' is not defined.";
                        var message = format.formatted(variableName);
                        return new CompileException(message);
                    });
        } else if (root.is(Node.Category.Boolean)) {
            return Primitive.Bool;
        } else if (root.is(Node.Category.Integer)) {
            var unformatted = List.<Type>apply(new IntegerType(true, 16));
            var value = root.apply(Attribute.Category.Value).asInteger();

            List<Type> formatted;
            if (value >= 0) {
                formatted = unformatted.add(new IntegerType(false, 16));
            } else {
                formatted = unformatted;
            }

            return new Union(formatted.add(new ReferenceType(Primitive.Void)));
        } else if (root.is(Node.Category.Return)) {
            var innerValue = root.apply(Attribute.Category.Value).asNode();
            return new MagmaResolver(innerValue, scope).resolve();
        } else if (root.is(Node.Category.Block)) {
            try {
                return root.apply(Attribute.Category.Children)
                        .asStreamOfNodes()
                        .foldRight(List.<Node>createList(), List::add)
                        .last()
                        .map(last -> new MagmaResolver(last, scope).resolve())
                        .orElse(Primitive.Void);
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        } else {
            throw new CompileException("Cannot resolve category of node: " + root);
        }
    }
}
