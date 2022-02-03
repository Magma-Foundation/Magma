package com.meti.app.compile.function;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.magma.MagmaTypeResolver;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

public record ImplementationResolver(Node node) implements Processor<Node> {
    @Override
    public Option<Node> process() throws CompileException {
        if (node.is(Node.Category.Implementation)) {
            var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
            var type = oldIdentity.apply(Attribute.Type.Type).asNode();
            if (type.is(Node.Category.Implicit)) {
                var value = node.apply(Attribute.Type.Value).asNode();
                var newType = new MagmaTypeResolver().transformNodeAST(value);
                var newIdentity = oldIdentity.with(Attribute.Type.Type, new NodeAttribute(newType));
                var newNode = node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
                return new Some<>(newNode);
            } else {
                return new None<>();
            }
        }
        return new None<>();
    }
}
