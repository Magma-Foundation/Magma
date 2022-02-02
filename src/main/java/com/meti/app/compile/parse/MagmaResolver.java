package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;

public record MagmaResolver(Node root, Scope scope) {
    List<Node> resolveNodeToMultiple() throws CompileException {
        if (root.is(Node.Type.Variable)) {
            var variableName = root.apply(Attribute.Type.Value).asInput()
                    .toOutput()
                    .compute();
            return List.apply(scope.lookup(variableName)
                    .map(node -> node.apply(Attribute.Type.Type).asNode())
                    .orElseThrow(() -> {
                        var format = "'%s' is not defined.";
                        var message = format.formatted(variableName);
                        return new CompileException(message);
                    }));
        } else if (root.is(Node.Type.Boolean)) {
            return List.apply(Primitive.Bool);
        } else if (root.is(Node.Type.Integer)) {
            var toReturn = List.<Node>apply(new IntegerType(true, 16));
            var value = root.apply(Attribute.Type.Value).asInteger();
            if (value >= 0) {
                return toReturn.add(new IntegerType(false, 16));
            } else {
                return toReturn;
            }
        } else if (root.is(Node.Type.Return)) {
            var innerValue = root.apply(Attribute.Type.Value).asNode();
            return new MagmaResolver(innerValue, scope).resolveNodeToMultiple();
        } else if (root.is(Node.Type.Block)) {
            try {
                return root.apply(Attribute.Type.Children)
                        .asStreamOfNodes()
                        .foldRight(List.<Node>createList(), List::add)
                        .last()
                        .map(last -> new MagmaResolver(last, scope).resolveNodeToMultiple())
                        .orElse(List.apply(Primitive.Void));
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        } else {
            throw new CompileException("Cannot resolve type of node: " + root);
        }
    }

    Node resolveNodeToSingle(Node value) throws CompileException {
        return resolveNodeToMultiple().first().orElseThrow(() -> {
            var format = "No types exist for node:\n%s";
            var message = format.formatted(value);
            return new CompileException(message);
        });
    }
}
