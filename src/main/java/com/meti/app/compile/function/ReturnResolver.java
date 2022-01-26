package com.meti.app.compile.function;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.magma.NodeStage;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

public record ReturnResolver(Node node, NodeStage parent) implements Processor<Node> {
    public Option<Node> process() throws CompileException {
        if (node.is(Node.Type.Return)) {
            var oldChild = node.apply(Attribute.Type.Value).asNode();
            var newChild = parent.transformNodeAST(oldChild);
            return new Some<>(newChild);
        } else {
            return new None<>();
        }
    }
}
