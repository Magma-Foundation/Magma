package com.meti.app.compile.scope;

import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.feature.util.Cache;
import com.meti.app.compile.feature.util.Line;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;

public record BlockTransformer(Node root) implements Processor<Node> {
    public Option<Node> process() throws CompileException {
        if (root.is(Node.Category.Block)) {
            var output = transformBlock(root);
            return new Some<>(output);
        } else {
            return new None<>();
        }
    }

    private static Node transformBlock(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Type.Children)
                    .asStreamOfNodes()
                    .map(BlockTransformer::transformInContext)
                    .foldRight(new Block.Builder(), Block.Builder::add)
                    .build();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private static Node transformInContext(Node oldChild) throws AttributeException {
        if (oldChild.is(Node.Category.Implementation)) {
            return new Cache(EmptyNode_, oldChild);
        }
        if (oldChild.is(Node.Category.Binary) || oldChild.is(Node.Category.Invocation)) return new Line(oldChild);
        if (oldChild.is(Node.Category.If)) {
            var value = oldChild.apply(Attribute.Type.Value).asNode();
            if (value.is(Node.Category.Block)) {
                return oldChild;
            } else {
                return oldChild.with(Attribute.Type.Value, new NodeAttribute(new Line(value)));
            }
        }
        return oldChild;
    }
}
