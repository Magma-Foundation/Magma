package com.meti.app.compile;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.node.Node;

record ReturnResolver(Node node, MagmaTypeResolver parent) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (node.is(Node.Type.Return)) {
            var oldChild = node.apply(Attribute.Type.Value).asNode();
            var newChild = parent.apply(oldChild);
            return new Some<>(newChild);
        } else {
            return new None<>();
        }
    }
}
