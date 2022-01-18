package com.meti.compile;

import com.meti.api.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.cache.Cache;
import com.meti.compile.common.Line;
import com.meti.compile.common.block.Block;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import static com.meti.compile.node.EmptyNode.EmptyNode_;

public record BlockTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (root.is(Node.Type.Block)) {
            var output = transformBlock(root);
            return new Some<>(output);
        } else {
            return new None<>();
        }
    }

    private static Node transformBlock(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .map(BlockTransformer::transformInContext)
                    .foldRight(new Block.Builder(), Block.Builder::add)
                    .build();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private static Node transformInContext(Node oldChild) throws AttributeException {
        if (oldChild.is(Node.Type.Implementation)) {
            return new Cache(EmptyNode_, oldChild);
        }
        if (oldChild.is(Node.Type.Binary) || oldChild.is(Node.Type.Invocation)) return new Line(oldChild);
        if (oldChild.is(Node.Type.If)) {
            var value = oldChild.apply(Attribute.Type.Value).asNode();
            if (value.is(Node.Type.Block)) {
                return oldChild;
            } else {
                return oldChild.with(Attribute.Type.Value, new NodeAttribute(new Line(value)));
            }
        }
        return oldChild;
    }
}
