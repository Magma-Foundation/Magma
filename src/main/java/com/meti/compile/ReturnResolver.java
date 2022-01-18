package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
