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
    public Type resolve() throws CompileException {
        if (root.is(Node.Role.Variable)) {
            var variableName = root.apply(Attribute.Type.Value).asInput()
                    .toOutput()
                    .compute();
            return scope.lookup(variableName)
                    .map(node -> node.apply(Attribute.Type.Type).asType())
                    .orElseThrow(() -> {
                        var format = "'%s' is not defined.";
                        var message = format.formatted(variableName);
                        return new CompileException(message);
                    });
        } else if (root.is(Node.Role.Boolean)) {
            return Primitive.Bool;
        } else if (root.is(Node.Role.Integer)) {
            var unformatted = List.<Type>apply(new ReferenceType(Primitive.Void), new IntegerType(true, 16));
            var value = root.apply(Attribute.Type.Value).asInteger();

            List<Type> formatted;
            if (value >= 0) {
                formatted = unformatted.add(new IntegerType(false, 16));
            } else {
                formatted = unformatted;
            }

            return new Union(formatted);
        } else if (root.is(Node.Role.Return)) {
            var innerValue = root.apply(Attribute.Type.Value).asNode();
            return new MagmaResolver(innerValue, scope).resolve();
        } else if (root.is(Node.Role.Block)) {
            try {
                return root.apply(Attribute.Type.Children)
                        .asStreamOfNodes()
                        .foldRight(List.<Node>createList(), List::add)
                        .last()
                        .map(last -> new MagmaResolver(last, scope).resolve())
                        .orElse(Primitive.Void);
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        } else {
            throw new CompileException("Cannot resolve type of node: " + root);
        }
    }
}
